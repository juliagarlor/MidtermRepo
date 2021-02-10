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
import java.time.*;
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
            Optional<AccountHolder> secondaryOwner = accountHolderRepository.findById(savingsAcDTO.getSecondaryOwnerId());
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

    @Override
    public SavingsAccount addAmount(long accountId, Money amount) {
        Optional<SavingsAccount> account = savingsAccountRepository.findById(accountId);

        if (amount.getAmount().signum() < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please, introduce a positive amount");
        }

        if (account.isPresent()){
            SavingsAccount output = account.get();

//            Increasing this account's balance. This method will return a BigDecimal, so we should store it
//            in a new Money variable in order to pass it to setBalance
            Money newBalance = new Money(output.getBalance().increaseAmount(amount));
            output.setBalance(newBalance);
            return savingsAccountRepository.save(output);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The account number is not correct. " +
                    "Please introduce a valid identifier");
        }
    }

    @Override
    public SavingsAccount subtractAmount(long accountId, Money amount) {
        Optional<SavingsAccount> account = savingsAccountRepository.findById(accountId);

        if (amount.getAmount().signum() < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please, introduce a positive amount");
        }

        if (account.isPresent()){
            SavingsAccount output = account.get();

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
            if (newBalance.getAmount().compareTo(output.getMinimumBalance().getAmount()) < 0){
                newBalance = new Money(newBalance.decreaseAmount(output.getPENALTY_FEE()));
            }

            output.setBalance(newBalance);
            return savingsAccountRepository.save(output);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The account number is not correct. " +
                    "Please introduce a valid identifier");
        }
    }

    public void applyInterest(long accountId){
        Optional<SavingsAccount> account = savingsAccountRepository.findById(accountId);

        if (account.isPresent()){
            SavingsAccount checked = account.get();
//            if more than one year has passed since the last appliance:
            int yearsToApply = Period.between(checked.getLastInterestRateApplied(), LocalDate.now()).getYears();

            if ( yearsToApply > 1){
//                Just in case we have not applied it since more than one year ago:

                for (int i = 0; i < yearsToApply; i++){
                    BigDecimal increment = checked.getInterestRate().multiply(checked.getBalance().getAmount());
                    Money newBalance = new Money(checked.getBalance().increaseAmount(increment));
                    checked.setBalance(newBalance);
                }
                savingsAccountRepository.save(checked);
            }
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The account number is not correct. " +
                    "Please introduce a valid identifier");
        }
    }
}
