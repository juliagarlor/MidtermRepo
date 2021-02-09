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
public class SavingsAccountService implements ISavingsAccountService {

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    public SavingsAccount createSavingsAccount(long userId, SavingsAcDTO savingsAcDTO) {
        Optional<AccountHolder> accountHolder = accountHolderRepository.findById(userId);
        if (accountHolder.isPresent()){

            AccountHolder primaryOwner = accountHolder.get();
            Money balance = new Money(savingsAcDTO.getBalance());
            Money minimumBalance = new Money(savingsAcDTO.getMinimumBalance());
            String secretKey = savingsAcDTO.getSecretKey();
            Optional<AccountHolder> secondaryOwner = savingsAcDTO.getSecondaryOwner();
            BigDecimal interestRate = savingsAcDTO.getInterestRate();

//            If we do not have a secondary owner, its variable will be set to null
            SavingsAccount newAccount = new SavingsAccount(balance, primaryOwner, secondaryOwner.get(), secretKey,
                    Status.ACTIVE, minimumBalance, interestRate);
            return savingsAccountRepository.save(newAccount);

        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The first owner identifier is not correct. " +
                    "Please introduce a valid identifier");
        }

    }
}
