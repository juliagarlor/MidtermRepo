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
    private UserRepository userRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    @Autowired
    private IAccountService iAccountService;

    public SavingsAccount createSavingsAccount(Long userId, SavingsAcDTO savingsAcDTO) {
        Optional<AccountHolder> accountHolder = accountHolderRepository.findById(userId);

        if (accountHolder.isPresent()){
//            If the user is a registered account holder, store it as the primary owner and extract the rest of the
//            parameters from the DTO
            AccountHolder primaryOwner = accountHolder.get();
            Money balance = new Money(savingsAcDTO.getBalance());

            Money minimumBalance = (savingsAcDTO.getMinimumBalance() == null) ?
                    new Money(new BigDecimal("1000"))
                    : new Money(savingsAcDTO.getMinimumBalance());

            String secretKey = savingsAcDTO.getSecretKey();
            Optional<AccountHolder> secondaryOwner = accountHolderRepository.findById(savingsAcDTO.getSecondaryOwnerId());

            BigDecimal interestRate = (savingsAcDTO.getInterestRate() == null)?
                    new BigDecimal("0.0025")
                    : savingsAcDTO.getInterestRate();

//            Create the new account, save it and return it
            SavingsAccount newAccount = new SavingsAccount(balance, Status.ACTIVE, primaryOwner, secondaryOwner.get(), secretKey,
                    minimumBalance, interestRate, LocalDate.now());
            return savingsAccountRepository.save(newAccount);

        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The first owner identifier is not correct. " +
                    "Please introduce a valid identifier");
        }
    }

    @Override
    public SavingsAccount checkAccount(Long accountId, Long userId) {
        Optional<SavingsAccount> account = savingsAccountRepository.findById(accountId);

        if (account.isPresent()){

//            Check if the logging in user is an admin, the primary or secondary owner of this account
            User client = userRepository.findById(userId).get();
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
//            if everything ok, check if interest should be applied and return
            applyInterest(accountId);
            return account.get();
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The account number is not correct. " +
                    "Please introduce a valid identifier");
        }
    }

    @Override
    public SavingsAccount addAmount(Long accountId, Money amount) {
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
    public SavingsAccount subtractAmount(Long accountId, Money amount) {
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

            output.setBalance(new Money(output.getBalance().decreaseAmount(amount)));

//            If the new balance is below minimum_balance, the penalty fee must be subtracted
            iAccountService.checkUnderMinimum(output);
            return savingsAccountRepository.save(output);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The account number is not correct. " +
                    "Please introduce a valid identifier");
        }
    }

    public void applyInterest(Long accountId){
        Optional<SavingsAccount> account = savingsAccountRepository.findById(accountId);

        if (account.isPresent()){
            SavingsAccount checked = account.get();
//            if more than one year has passed since the last appliance:
            int yearsToApply = Period.between(checked.getLastInterestRateApplied(), LocalDate.now()).getYears();

            if ( yearsToApply >= 1){
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
