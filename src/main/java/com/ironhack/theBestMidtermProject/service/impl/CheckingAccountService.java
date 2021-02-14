package com.ironhack.theBestMidtermProject.service.impl;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.model.users.*;
import com.ironhack.theBestMidtermProject.repository.accounts.*;
import com.ironhack.theBestMidtermProject.repository.users.*;
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
    private UserRepository userRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private CheckingAccountRepository checkingAccountRepository;

    @Autowired
    private IAccountService iAccountService;

    public CheckingAccount checkAccount(Long accountId, Long userId) {
//        Looking for the account
        Optional<CheckingAccount> account = checkingAccountRepository.findById(accountId);

        if (account.isPresent()){
//            The client must exist in our database, because otherwise, the unauthorized response status will appear while logging
            User client = userRepository.findById(userId).get();
            boolean isAdmin = client.getRoles().stream().anyMatch(x ->x.getName().equals("ADMIN"));

            if (!isAdmin) {
//                if the user is not an admin, check whether it is the primary owner (the userId must be in the repository,
//                and the accountId must be inside its primaryAccounts list)
                Optional<AccountHolder> clientConfirmation = accountHolderRepository.findByIdAndPrimaryAccountsId(userId, accountId);
                if (!clientConfirmation.isPresent()) {
//                    if not the primary owner, try with the secondary
                    clientConfirmation = accountHolderRepository.findByIdAndSecondaryAccountsId(userId, accountId);
                    if (!clientConfirmation.isPresent()) {
//                        if none of them, I am sorry
                        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to see this data");
                    }
                }
            }
//            else, return the account
            return account.get();
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The account number is not correct. " +
                    "Please introduce a valid identifier");
        }
    }

    public CheckingAccount addAmount(Long accountId, Money amount) {
//        Looking for the account
        Optional<CheckingAccount> account = checkingAccountRepository.findById(accountId);

//        You can not continue if the transference amount is negative
        if (amount.getAmount().signum() < 0){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please, introduce a positive amount");
        }

        if (account.isPresent()){
            CheckingAccount output = account.get();

//            Increasing this account's balance
            Money newBalance = new Money(output.getBalance().increaseAmount(amount));
            output.setBalance(newBalance);
            return checkingAccountRepository.save(output);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The account number is not correct. " +
                    "Please introduce a valid identifier");
        }
    }

    public CheckingAccount subtractAmount(Long accountId, Money amount) {
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

            output.setBalance(new Money(output.getBalance().decreaseAmount(amount)));

//            If the new balance is below minimum_balance, the penalty fee must be subtracted
            iAccountService.checkUnderMinimum(output);

            return checkingAccountRepository.save(output);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The account number is not correct. " +
                    "Please introduce a valid identifier");
        }
    }
}
