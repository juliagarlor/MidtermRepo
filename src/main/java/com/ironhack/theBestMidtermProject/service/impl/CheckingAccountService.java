package com.ironhack.theBestMidtermProject.service.impl;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.repository.accounts.*;
import com.ironhack.theBestMidtermProject.service.interfaces.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.server.*;

import java.math.*;
import java.util.*;

@Service
public class CheckingAccountService implements ICheckingAccountService {

    @Autowired
    private CheckingAccountRepository checkingAccountRepository;

    @Override
    public CheckingAccount addAmount(long accountId, Money amount) {
        Optional<CheckingAccount> account = checkingAccountRepository.findById(accountId);

        if (amount.getAmount().signum() < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please, introduce a positive amount");
        }

        if (account.isPresent()){
            CheckingAccount output = account.get();

//            Increasing this account's balance. This method will return a BigDecimal, so we should store it
//            in a new Money variable in order to pass it to setBalance
            Money newBalance = new Money(output.getBalance().increaseAmount(amount));
            output.setBalance(newBalance);
            return checkingAccountRepository.save(output);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The account number is not correct. " +
                    "Please introduce a valid identifier");
        }
    }

    @Override
    public CheckingAccount subtractAmount(long accountId, Money amount) {
        Optional<CheckingAccount> account = checkingAccountRepository.findById(accountId);

        if (amount.getAmount().signum() < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please, introduce a positive amount");
        }

        if (account.isPresent()){
            CheckingAccount output = account.get();

//            We should first check if we have enough money to subtract.
//            Although we can be in red numbers, I want the client to know
            BigDecimal currentBalance = output.getBalance().getAmount();

            if (currentBalance.compareTo(amount.getAmount()) < 0){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "As broke as Donald Trump");
            }
//            Decreasing this account's balance. This method will return a BigDecimal, so we should store it
//            in a new Money variable in order to pass it to setBalance

            Money newBalance = new Money(output.getBalance().decreaseAmount(amount));

//            If the new balance is below minimum_balance, the penalty fee must be subtracted
            if (newBalance.getAmount().compareTo(output.getMINIMUM_BALANCE().getAmount()) < 0){
                newBalance = new Money(newBalance.decreaseAmount(output.getPENALTY_FEE()));
            }

            output.setBalance(newBalance);
            return checkingAccountRepository.save(output);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The account number is not correct. " +
                    "Please introduce a valid identifier");
        }
    }
}
