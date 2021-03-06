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
public class CreditCardService implements ICreditCardService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private CreditCardAccountRepository creditAccountRepository;

    public CreditCardAccount checkAccount(Long accountId, Long userId) {
        Optional<CreditCardAccount> account = creditAccountRepository.findById(accountId);

        if (account.isPresent()){

            User client = userRepository.findById(userId).get();
//            Checking whether is an admin, the primary or the secondary owner of the account
            boolean isAdmin = client.getRoles().stream().anyMatch(x ->x.getName().equals("ADMIN"));

            if (!isAdmin) {
                Optional<AccountHolder> clientConfirmation = accountHolderRepository.findByIdAndPrimaryAccountsId(userId, accountId);
                if (!clientConfirmation.isPresent()) {
                    clientConfirmation = accountHolderRepository.findByIdAndSecondaryAccountsId(userId, accountId);
                    if (!clientConfirmation.isPresent()) {
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to see this data");
                    }
                }
            }
//            If everything is ok, check for interests, apply if necessary and save
            CreditCardAccount output = account.get();
            Money newBalance = applyInterest(accountId);
            output.setBalance(newBalance);
            return creditAccountRepository.save(output);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The account number is not correct. " +
                    "Please introduce a valid identifier");
        }
    }

    public CreditCardAccount createCreditAccount(Long userId, CreditAcDTO creditAcDTO) {

        Optional<AccountHolder> accountHolder = accountHolderRepository.findById(userId);

        if (accountHolder.isPresent()){
//            if the user is a registered account holder, save it as the primary owner, and get everything from the DTO
            AccountHolder primaryOwner = accountHolder.get();
            Money balance = new Money(creditAcDTO.getBalance());
            Money monthlyMaintenanceFee = new Money(creditAcDTO.getMonthlyMaintenanceFee());
            Optional<AccountHolder> secondaryOwner = accountHolderRepository.findById(creditAcDTO.getSecondaryOwnerId());

            Money creditLimit = (creditAcDTO.getCreditLimit() == null) ?
                    new Money(new BigDecimal("100.0000")) :
                    new Money(creditAcDTO.getCreditLimit());

            BigDecimal interestRate = (creditAcDTO.getInterestRate() == null) ?
                    new BigDecimal("0.2000") :
                    creditAcDTO.getInterestRate();

//            And construct the new account, save it and return
            CreditCardAccount newAccount = new CreditCardAccount(balance, Status.ACTIVE, primaryOwner, secondaryOwner.get(),
                    monthlyMaintenanceFee, interestRate, creditLimit, LocalDate.now());
            return creditAccountRepository.save(newAccount);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The first owner identifier is not correct. " +
                    "Please introduce a valid identifier");
        }
    }

    public CreditCardAccount addAmount(Long accountId, Money amount) {
        Optional<CreditCardAccount> account = creditAccountRepository.findById(accountId);

        if (amount.getAmount().signum() < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please, introduce a positive amount");
        }

        if (account.isPresent()){
            CreditCardAccount output = account.get();

//            Increasing this account's balance. This method will return a BigDecimal, so we should store it
//            in a new Money variable in order to pass it to setBalance
            Money newBalance = new Money(output.getBalance().increaseAmount(amount));
            output.setBalance(newBalance);
            return creditAccountRepository.save(output);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The account number is not correct. " +
                    "Please introduce a valid identifier");
        }
    }

    public CreditCardAccount subtractAmount(Long accountId, Money amount) {
        Optional<CreditCardAccount> account = creditAccountRepository.findById(accountId);

        if (amount.getAmount().signum() < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please, introduce a positive amount");
        }

        if (account.isPresent()){
            CreditCardAccount output = account.get();

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
            return creditAccountRepository.save(output);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The account number is not correct. " +
                    "Please introduce a valid identifier");
        }
    }

    public Money applyInterest(Long accountId){
        Optional<CreditCardAccount> account = creditAccountRepository.findById(accountId);

        if (account.isPresent()){
            CreditCardAccount checked = account.get();
//            We will apply both interestRate and MonthlyMaintenanceFee using LastInterestRateApplied
//            if more than one month has passed since the last appliance:
            int monthsToApply = Period.between(checked.getLastInterestRateApplied(), LocalDate.now()).getMonths();

            if ( monthsToApply >= 1){
//                The interest rate is applied monthly, so we should divide it in 12:
                BigDecimal interestRateToApply = checked.getInterestRate().divide(new BigDecimal("12"), 4, RoundingMode.HALF_UP);

                Money newBalance = new Money(new BigDecimal("0"));
//                Just in case we have not applied it since more than one year ago:
                for (int i = 0; i < monthsToApply; i++){
                    BigDecimal increment = interestRateToApply.multiply(checked.getBalance().getAmount());
//                    Applying interestRate:
                    newBalance = new Money(checked.getBalance().increaseAmount(increment));
//                    Applying monthlyMaintenanceFee:
                    newBalance = new Money(newBalance.decreaseAmount(checked.getMonthlyMaintenanceFee()));
                    checked.setBalance(newBalance);
                }
                return newBalance;
            }
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The account number is not correct. " +
                    "Please introduce a valid identifier");
        }
        return null;
    }
}
