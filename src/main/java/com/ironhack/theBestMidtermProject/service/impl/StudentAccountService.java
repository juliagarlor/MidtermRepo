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
public class StudentAccountService implements IStudentAccountService {

    @Autowired
    private StudentCheckingAccountRepository studentCheckingAccountRepository;

    @Override
    public StudentCheckingAccount addAmount(long accountId, Money amount) {
        Optional<StudentCheckingAccount> account = studentCheckingAccountRepository.findById(accountId);

        if (amount.getAmount().signum() < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please, introduce a positive amount");
        }

        if (account.isPresent()){
            StudentCheckingAccount output = account.get();

//            Increasing this account's balance. This method will return a BigDecimal, so we should store it
//            in a new Money variable in order to pass it to setBalance
            Money newBalance = new Money(output.getBalance().increaseAmount(amount));
            output.setBalance(newBalance);
            return studentCheckingAccountRepository.save(output);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The account number is not correct. " +
                    "Please introduce a valid identifier");
        }
    }

    @Override
    public StudentCheckingAccount subtractAmount(long accountId, Money amount) {
        Optional<StudentCheckingAccount> account = studentCheckingAccountRepository.findById(accountId);

        if (amount.getAmount().signum() < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please, introduce a positive amount");
        }

        if (account.isPresent()){
            StudentCheckingAccount output = account.get();

//            We should first check if we have enough money to subtract.
//            Although we can be in red numbers, I want the client to know
            BigDecimal currentBalance = output.getBalance().getAmount();

            if (currentBalance.compareTo(amount.getAmount()) < 0){
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "As broke as Donald Trump");
            }
//            Decreasing this account's balance. This method will return a BigDecimal, so we should store it
//            in a new Money variable in order to pass it to setBalance

            Money newBalance = new Money(output.getBalance().decreaseAmount(amount));
            output.setBalance(newBalance);
            return studentCheckingAccountRepository.save(output);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The account number is not correct. " +
                    "Please introduce a valid identifier");
        }
    }
}
