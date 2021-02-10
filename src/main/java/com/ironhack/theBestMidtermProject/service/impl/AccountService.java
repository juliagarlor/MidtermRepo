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

import java.math.*;
import java.time.*;
import java.util.*;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private AccountHolderRepository accountHolderRepository;

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

//      Check if both emisor and receptor accounts exists:
        Optional<Account> emisorAccountOp = accountRepository.findById(transactionsDTO.getEmisor());
        Optional<Account> receptorAccountOp = accountRepository.findById(transactionsDTO.getReceptor());

        if(emisorAccountOp.isPresent() && receptorAccountOp.isPresent() && receptorAccountOp.get() != emisorAccountOp.get()){
            Account emisorAccount = emisorAccountOp.get();
            Account receptorAccount = receptorAccountOp.get();

//            We suppose that the one logging is the emisor
            if (emisorAccount.getPrimaryOwner().getId() == emisorId){
                Money amount = new Money(transactionsDTO.getAmount());
                LocalDateTime moment = transactionsDTO.getMoment();

                Transactions transactions = new Transactions(emisorAccount, receptorAccount, amount, moment);

                if (fraudChecker.checkFraudInADay(transactions) || fraudChecker.checkFraudInLastSecond(transactions)){
                    throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "You may need money for a ticket," +
                            " but it is not going to come from this bank. Good luck with the police :)");
                }else {

//                    TODO: HAZ ALGO POR AQUI
                    return transactionRepository.save(transactions);
                }

            }else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please, transfer from your own account.");
            }
        }else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The account identifiers must be valid and different.");
        }


    }
}
