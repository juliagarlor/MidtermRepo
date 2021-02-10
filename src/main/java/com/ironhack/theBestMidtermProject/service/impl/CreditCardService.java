package com.ironhack.theBestMidtermProject.service.impl;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.model.users.*;
import com.ironhack.theBestMidtermProject.repository.accounts.*;
import com.ironhack.theBestMidtermProject.repository.users.*;
import com.ironhack.theBestMidtermProject.service.interfaces.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.server.*;

import java.math.*;
import java.util.*;

@Service
public class CreditCardService implements ICreditCardService {
    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private CreditCardAccountRepository creditAccountRepository;

    public CreditCardAccount createCreditAccount(long userId, CreditAcDTO creditAcDTO) {
//        Find account owner whose account will be created
        Optional<AccountHolder> accountHolder = accountHolderRepository.findById(userId);
//        if exists
        if (accountHolder.isPresent()){
            AccountHolder primaryOwner = accountHolder.get();
            Money balance = new Money(creditAcDTO.getBalance());
            Money monthlyMaintenanceFee = new Money(creditAcDTO.getMonthlyMaintenanceFee());
            Optional<AccountHolder> secondaryOwner = accountHolderRepository.findById(creditAcDTO.getSecondaryOwnerId());

            Money creditLimit;
            if (creditAcDTO.getCreditLimit() == null){
                creditLimit = new Money(new BigDecimal("100"));
            } else {
                creditLimit = new Money(creditAcDTO.getCreditLimit());
            }
            BigDecimal interestRate;
            if (creditAcDTO.getInterestRate() == null){
                interestRate =  new BigDecimal("0.2");
            } else {
                interestRate = creditAcDTO.getInterestRate();
            }

//            If we do not have a secondary owner, its variable will be set to null
            CreditCardAccount newAccount = new CreditCardAccount(balance, primaryOwner, secondaryOwner.get(),
                    monthlyMaintenanceFee, interestRate, creditLimit);
            return creditAccountRepository.save(newAccount);
//            return new CreditCardAccount();
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The first owner identifier is not correct. " +
                    "Please introduce a valid identifier");
        }
    }
}
