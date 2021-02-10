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


//    todo QUE HACEMOS SI NOS PASAN UN AMOUNT NEGATIVO? SOMOS FLEXIBLES Y LO CORREGIMOS O INFORMAMOS AL USER?
//    todo SI GUARDO UNA CUENTA CUALQUIERA EN ACCOUNTREPOSITORY, ME LA GUARDARÁ COMO ACCOUNT O COMO LA CUENTA QUE ES?

    public Account addAmount(long accountId, Money amount) {
        Optional<Account> account = accountRepository.findById(accountId);

        if (account.isPresent()){
            Account output = account.get();

//            We make sure it is a positive amount
            BigDecimal amountToAbs = amount.getAmount().abs();

//            Increasing this account's balance. This method will return a BigDecimal, so we should store it
//            in a new Money variable in order to pass it to setBalance
            Money newBalance = new Money(output.getBalance().increaseAmount(amountToAbs));
            output.setBalance(newBalance);
            return accountRepository.save(output);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The account number is not correct. " +
                    "Please introduce a valid identifier");
        }
    }

    //    TODO SI CAMBIAS EL SAVE ARRIBA, ACUERDATE DE CAMBIARLO AQUI TAMBIEN
    public Account subtractAmount(long accountId, Money amount){
        Optional<Account> account = accountRepository.findById(accountId);

        if (account.isPresent()){
            Account output = account.get();

//            We make sure it is a positive amount
            BigDecimal amountToAbs = amount.getAmount().abs();

//            We should first check if we have enough money to subtract.
//            Although we can be in red numbers, I want the client to know
            BigDecimal currentBalance = output.getBalance().getAmount();

            if (currentBalance.compareTo(amount.getAmount()) < 0){
//                todo PON ALGO PARA QUE EL CLIENTE SEPA QUE ESTÁ EN NÚMEROS ROJOS. PREGUNTA:SI HAGO UN THROW AQUÍ, NO PASA A LA SIGUIENTE LINEA?
            }
//            Decreasing this account's balance. This method will return a BigDecimal, so we should store it
//            in a new Money variable in order to pass it to setBalance

            Money newBalance = new Money(output.getBalance().decreaseAmount(amount));
            output.setBalance(newBalance);
            return output;
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The account number is not correct. " +
                    "Please introduce a valid identifier");
        }
    }
}
