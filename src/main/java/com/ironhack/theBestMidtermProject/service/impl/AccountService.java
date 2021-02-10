package com.ironhack.theBestMidtermProject.service.impl;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.model.users.*;
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

    public Account createCheckAccount(long userId, CheckingAcDTO checkingAcDTO){
        Optional<AccountHolder> accountHolder = accountHolderRepository.findById(userId);
        if (accountHolder.isPresent()){

            AccountHolder primaryOwner = accountHolder.get();
            Money balance = new Money(checkingAcDTO.getBalance());
            String secretKey = checkingAcDTO.getSecretKey();
            Optional<AccountHolder> secondaryOwner = accountHolderRepository.findById(checkingAcDTO.getSecondaryOwner());

            if(primaryOwner.getAge() < 24){
                StudentCheckingAccount newAccount = new StudentCheckingAccount(balance, primaryOwner, secondaryOwner.get(),
                        secretKey, Status.ACTIVE);
                return studentAccountRepository.save(newAccount);
            }else {
                CheckingAccount newAccount = new CheckingAccount(balance, primaryOwner, secondaryOwner.get(), secretKey,
                        Status.ACTIVE);
                return checkingAccountRepository.save(newAccount);
            }
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The first owner identifier is not correct. " +
                    "Please introduce a valid identifier");
        }
    }
}
