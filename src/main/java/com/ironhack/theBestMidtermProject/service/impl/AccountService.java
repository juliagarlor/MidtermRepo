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
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;
import org.springframework.stereotype.*;
import org.springframework.web.server.*;

import java.time.*;
import java.util.*;

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
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private FraudChecker fraudChecker;


    public Account createCheckAccount(long userId, CheckingAcDTO checkingAcDTO){
        Optional<AccountHolder> accountHolder = accountHolderRepository.findById(userId);
        if (accountHolder.isPresent()){

            AccountHolder primaryOwner = accountHolder.get();
            Money balance = new Money(checkingAcDTO.getBalance());
            String secretKey = checkingAcDTO.getSecretKey();
            Optional<AccountHolder> secondaryOwner = accountHolderRepository.findById(checkingAcDTO.getSecondaryOwner());

            if(primaryOwner.getAge() < 24){
                StudentCheckingAccount newAccount = new StudentCheckingAccount(balance, Status.ACTIVE, primaryOwner, secondaryOwner.get(),
                         secretKey);
                return studentAccountRepository.save(newAccount);
            }else {
                CheckingAccount newAccount = new CheckingAccount(balance, Status.ACTIVE, primaryOwner, secondaryOwner.get(), secretKey);
                return checkingAccountRepository.save(newAccount);
            }
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The first owner identifier is not correct. " +
                    "Please introduce a valid identifier");
        }
    }

    public Transactions transfer(long emisorId, TransactionsDTO transactionsDTO){

//      Check if both emisor and receptor accounts exists or not:
        Optional<Account> emisorAccountOp = accountRepository.findById(transactionsDTO.getEmisor());
        Optional<Account> receptorAccountOp = accountRepository.findById(transactionsDTO.getReceptor());

        if((emisorAccountOp.isPresent() && receptorAccountOp.isPresent()) && receptorAccountOp.get() != emisorAccountOp.get()){

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

//            We suppose that the one logging is the emisor
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

                Transactions transactions = new Transactions(emisorAccount, receptorAccount, amount, moment);

                if (fraudChecker.checkFraudInADay(transactions) || fraudChecker.checkFraudInLastSecond(transactions)){
                    emisorAccount.setStatus(Status.FROZEN);
                    throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "You may need money for a ticket," +
                            " but it is not going to come from this bank. Good luck with the police :)");
                }else {
//                    We will assume this is not a transaction with a third party, so:
//                    Decreasing balance from emisor:
                    emisorAccount.setBalance(new Money(emisorAccount.getBalance().decreaseAmount(amount)));
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

    public Transactions transferWithThirdParty(String hashedKey, String accountSecretKey, long thirdPartyId,
                                               TransactionsDTO transactionsDTO) {
//        Checking whether the hashedKey is correct for this thirdParty
        boolean passing = thirdPartyRepository.findById(thirdPartyId).get().getHashedKey().equals(hashedKey);

        if (passing){
//            Checking which of them is the third party and whether the ids are valid:
            Optional<Account> emisorAccountOp = Optional.empty();
            Optional<Account> receptorAccountOp = Optional.empty();

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
                    accountRepository.save(emisorAccount);
                }
            }
//                    Saving and returning the transaction:
            return transactionRepository.save(transactions);

        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The hashed key is not correct.");
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
