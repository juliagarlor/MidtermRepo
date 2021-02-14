package com.ironhack.theBestMidtermProject.service.impl;

import com.ironhack.theBestMidtermProject.model.*;
import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.model.users.*;
import com.ironhack.theBestMidtermProject.repository.*;
import com.ironhack.theBestMidtermProject.repository.accounts.*;
import com.ironhack.theBestMidtermProject.repository.users.*;
import com.ironhack.theBestMidtermProject.service.interfaces.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;
import com.ironhack.theBestMidtermProject.utils.enums.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.server.*;

import java.time.*;
import java.util.*;

//Sorry for the long class :)

@Service
public class AccountService implements IAccountService {

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    @Autowired
    private StudentCheckingAccountRepository studentAccountRepository;

    @Autowired
    private CheckingAccountRepository checkingAccountRepository;

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private FraudChecker fraudChecker;


    public Account createCheckAccount(Long userId, CheckingAcDTO checkingAcDTO){

//        Looking for the accountHolder
        Optional<AccountHolder> accountHolder = accountHolderRepository.findById(userId);

        if (accountHolder.isPresent()){

//            If this account holder is present in our database, it will be considered as the first owner of the new
//            account. Taking every other necessary parameter from the DTO
            AccountHolder primaryOwner = accountHolder.get();
            Money balance = new Money(checkingAcDTO.getBalance());
            String secretKey = checkingAcDTO.getSecretKey();
//            Looking for the secondary owner. If it is not inside our database, or the DTO value is simply set to null,
//            the new account will not have a secondary owner
            Optional<AccountHolder> secondaryOwner = accountHolderRepository.findById(checkingAcDTO.getSecondaryOwner());

            if(primaryOwner.getAge() < 24){
//                if the primary owner is under 24, we will create an student account
                StudentCheckingAccount newAccount = new StudentCheckingAccount(balance, Status.ACTIVE, primaryOwner,
                        secondaryOwner.get(), secretKey);
                return studentAccountRepository.save(newAccount);
            }else {
//                else, we will return a checking account
                CheckingAccount newAccount = new CheckingAccount(balance, Status.ACTIVE, primaryOwner, secondaryOwner.get(), secretKey);
                return checkingAccountRepository.save(newAccount);
            }
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The first owner identifier is not correct. " +
                    "Please introduce a valid identifier");
        }
    }

    public Transactions transfer(Long emisorId, TransactionsDTO transactionsDTO){

//      Check if both emisor and receptor accounts exists or not:
        Optional<Account> emisorAccountOp = accountRepository.findById(transactionsDTO.getEmisor());
        Optional<Account> receptorAccountOp = accountRepository.findById(transactionsDTO.getReceptor());

        if((emisorAccountOp.isPresent() && receptorAccountOp.isPresent()) && receptorAccountOp.get() != emisorAccountOp.get()){

//            if emisor and receptor accounts are both registered in our database and both are different, we store them
//            in two variables:
            Account emisorAccount = emisorAccountOp.get();
            Account receptorAccount = receptorAccountOp.get();

//            We can not transfer money to a credit card account, so lets check that none of them are:
            Class emisorAccountClass = emisorAccount.getClass();
            Class receptorAccountClass = receptorAccount.getClass();

            if (emisorAccountClass.equals(CreditCardAccount.class) || receptorAccountClass.equals(CreditCardAccount.class)){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Credit cards should not be used for transferences.");
            }

//            Then casting to their respective classes:
            emisorAccountClass.cast(emisorAccount);
            receptorAccountClass.cast(receptorAccount);

//            We suppose that the one logging in is the emisor
            if (emisorAccount.getPrimaryOwner().getId() == emisorId){
                Money amount = new Money(transactionsDTO.getAmount());

//                Checking if the emisor is FROZEN
                if (emisorAccount.getStatus() == Status.FROZEN){
                    throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "I don't think so");
                }

//                Checking if the emisor has enough funds:
                if (emisorAccount.getBalance().getAmount().compareTo(amount.getAmount()) < 0){
                    throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "The emisor account does not have enough funds");
                }

                LocalDateTime moment = transactionsDTO.getMoment();
//                And forming the transactions
                Transactions transactions = new Transactions(emisorAccount, receptorAccount, amount, moment);

//                Checking for fraud
                if (fraudChecker.checkFraudInADay(transactions) || fraudChecker.checkFraudInLastSecond(transactions)){
                    emisorAccount.setStatus(Status.FROZEN);
                    throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "You may need money for a ticket," +
                            " but it is not going to come from this bank. Good luck with the police :)");
                }else {
//                    Decreasing balance from emisor:
                    emisorAccount.setBalance(new Money(emisorAccount.getBalance().decreaseAmount(amount)));
//                    Checking if the emisor has gone under minimumBalance:
                    checkUnderMinimum(emisorAccount);
                    accountRepository.save(emisorAccount);

//                    Increasing balance from receptor:
                    receptorAccount.setBalance(new Money(receptorAccount.getBalance().increaseAmount(amount)));
                    accountRepository.save(receptorAccount);

//                    Saving and returning the transaction:
                    return transactionRepository.save(transactions);
                }
            }else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please, do this operations from your own account.");
            }
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The account identifiers must be valid and different.");
        }
    }

    public Transactions transferWithThirdParty(String hashedKey, String accountSecretKey, Long thirdPartyId,
                                               TransactionsDTO transactionsDTO) {
//        Checking whether the hashedKey is correct for this thirdParty
        boolean passing = thirdPartyRepository.findById(thirdPartyId).get().getHashedKey().equals(hashedKey);

        if (passing){
//            Checking which of them is the third party and whether the ids are valid:
            Optional<Account> emisorAccountOp = Optional.empty();
            Optional<Account> receptorAccountOp = Optional.empty();

//            Updating depending on whether the third party is the emisor or the receptor (the one in null)
            if (transactionsDTO.getEmisor() == null){
                receptorAccountOp = accountRepository.findById(transactionsDTO.getReceptor());
            }else {
                emisorAccountOp = accountRepository.findById(transactionsDTO.getEmisor());
            }

            Account emisorAccount = new Account();
            Account receptorAccount = new Account();
//            One of those Ops must be null: either because the third party has not written its account id or because it
//            is not in our accountRepository. The problem would be if both ops are null
            if (emisorAccountOp.isEmpty() && receptorAccountOp.isEmpty()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please introduce a valid account identifier.");
            }else if(emisorAccountOp.isEmpty()){
                emisorAccount = null;
                receptorAccount = receptorAccountOp.get();
//            We can not transfer money to a credit card account, so lets check that none of them are:
                castToOtherThanCreditAndCheckSecretKey(receptorAccount, accountSecretKey);
            }else {
                emisorAccount = emisorAccountOp.get();
                receptorAccount = null;
                castToOtherThanCreditAndCheckSecretKey(emisorAccount, accountSecretKey);
            }

//            Now we can form the real transaction:
            Money amount = new Money(transactionsDTO.getAmount());
            LocalDateTime moment = transactionsDTO.getMoment();
            Transactions transactions = new Transactions(emisorAccount, receptorAccount, amount, moment);

//            We can only check for fraud if the emisor is not the third party, so:
            if (emisorAccount == null){
//                Case: third party is the emisor:
//                Increasing balance from receptor:
                receptorAccount.setBalance(new Money(receptorAccount.getBalance().increaseAmount(amount)));
                accountRepository.save(receptorAccount);
            }else {
//                Case: third party is the receptor:
//                Checking if the emisor has enough funds:
                if (emisorAccount.getBalance().getAmount().compareTo(amount.getAmount()) < 0){
                    throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "The emisor account does not have enough funds");
                }
//                Check for fraud:
                if (fraudChecker.checkFraudInADay(transactions) || fraudChecker.checkFraudInLastSecond(transactions)){
                    emisorAccount.setStatus(Status.FROZEN);
                    throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "You may need money for a ticket," +
                            " but it is not going to come from this bank. Good luck with the police :)");
                }else {
//                    Decreasing balance from emisor:
                    emisorAccount.setBalance(new Money(emisorAccount.getBalance().decreaseAmount(amount)));
//                    checking under minimum balance:
                    checkUnderMinimum(emisorAccount);
                    accountRepository.save(emisorAccount);
                }
            }
//                    Saving and returning the transaction:
            return transactionRepository.save(transactions);

        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The hashed key is not correct.");
        }
    }

    public void checkUnderMinimum(Account account){
//        only checking and savings accounts have minimumBalance
        Class accountClass = account.getClass();

        if (CheckingAccount.class.equals(accountClass)){
//            if it's a checking account, we cast it so we can take out the minimumBalance:
            CheckingAccount.class.cast(account);
            if (((CheckingAccount) account).getMINIMUM_BALANCE().getAmount().compareTo(account.getBalance().getAmount()) > 0){
//                if minimum balance is bigger than the current balance, subtract the penalty fee. I am so sorry for this:
                account.setBalance(new Money(account.getBalance().decreaseAmount(account.getPENALTY_FEE())));
                checkingAccountRepository.save((CheckingAccount) account);
            }
        } else if(SavingsAccount.class.equals(accountClass)){
//            We do the same as above:
            SavingsAccount.class.cast(account);
            if (((SavingsAccount) account).getMinimumBalance().getAmount().compareTo(account.getBalance().getAmount()) > 0){
                account.setBalance(new Money(account.getBalance().decreaseAmount(account.getPENALTY_FEE())));
                savingsAccountRepository.save((SavingsAccount) account);
            }
        }
    }

    public void castToOtherThanCreditAndCheckSecretKey(Account account, String secretKey){
        Class accountClass = account.getClass();

        if (accountClass.equals(CreditCardAccount.class)){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Credit cards should not be used for transferences.");
        }

//            Then casting to their respective classes:
        if (CheckingAccount.class.equals(accountClass)) {
            CheckingAccount.class.cast(account);

            if (!((CheckingAccount) account).getSecretKey().equals(secretKey)){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Incorrect account secretKey.");
            }
        } else if (StudentCheckingAccount.class.equals(accountClass)){
            StudentCheckingAccount.class.cast(account);

            if (!((StudentCheckingAccount) account).getSecretKey().equals(secretKey)){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Incorrect account secretKey.");
            }
        } else if (SavingsAccount.class.equals(accountClass)){
            SavingsAccount.class.cast(account);

            if (!((SavingsAccount) account).getSecretKey().equals(secretKey)){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Incorrect account secretKey.");
            }
        }
    }
}
