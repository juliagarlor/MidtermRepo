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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.*;

import javax.persistence.*;
import javax.validation.*;
import javax.validation.constraints.*;
import java.math.*;
import java.util.*;

@Service
public class AdminService implements IAdminService {
    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CheckingAccountRepository checkingAccountRepository;

    @Autowired
    private StudentCheckingAccountRepository studentAccountRepository;

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    @Autowired
    private CreditCardAccountRepository creditAccountRepository;

    @Autowired
    private AccountRepository accountRepository;

    private Ensambler ensambler;

    public Admin createAdmin(AdminDTO adminDTO){
        Name name = ensambler.ensambleName(adminDTO.getNameDTO());
        int age = adminDTO.getAge();
        Set<Role> roles = new HashSet<>();

        Admin newAdmin = new Admin(name, age, roles);

        roles.add(new Role("USER", newAdmin));
        newAdmin.setRoles(roles);
        return adminRepository.save(newAdmin);
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

    public Account createCheckAccount(long userId, CheckingAcDTO checkingAcDTO){
        Optional<AccountHolder> accountHolder = accountHolderRepository.findById(userId);
        if (accountHolder.isPresent()){

            AccountHolder primaryOwner = accountHolder.get();
            Money balance = new Money(checkingAcDTO.getBalance());
            String secretKey = checkingAcDTO.getSecretKey();
            Optional<AccountHolder> secondaryOwner = checkingAcDTO.getSecondaryOwner();

            if(primaryOwner.getAge() < 24){
                StudentCheckingAccount newAccount = new StudentCheckingAccount(balance, primaryOwner, secondaryOwner.get(),
                        secretKey, Status.ACTIVE);
                if (secondaryOwner.isPresent()){
                    secondaryOwner.get().addSecondaryAccount(newAccount);
                }
                primaryOwner.addPrimaryAccount(newAccount);
                return studentAccountRepository.save(newAccount);
            }else {
                CheckingAccount newAccount = new CheckingAccount(balance, primaryOwner, secondaryOwner.get(), secretKey,
                        Status.ACTIVE);
                if (secondaryOwner.isPresent()){
                    secondaryOwner.get().addSecondaryAccount(newAccount);
                }
                primaryOwner.addPrimaryAccount(newAccount);
                return checkingAccountRepository.save(newAccount);
            }
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The first owner identifier is not correct. " +
                    "Please introduce a valid identifier");
        }
    }

    public Account createSavingsAccount(long userId, SavingsAcDTO savingsAcDTO){
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

    public Account createCreditAccount(long userId, CreditAcDTO creditAcDTO) {
        Optional<AccountHolder> accountHolder = accountHolderRepository.findById(userId);
        if (accountHolder.isPresent()){

            AccountHolder primaryOwner = accountHolder.get();
            Money balance = new Money(creditAcDTO.getBalance());
            Money monthlyMaintenanceFee = new Money(creditAcDTO.getMonthlyMaintenanceFee());
            BigDecimal interestRate = creditAcDTO.getInterestRate();
            Optional<AccountHolder> secondaryOwner = creditAcDTO.getSecondaryOwner();
            Money creditLimit = new Money(creditAcDTO.getCreditLimit());

//            If we do not have a secondary owner, its variable will be set to null
            CreditCardAccount newAccount = new CreditCardAccount(balance, primaryOwner, secondaryOwner.get(),
                    monthlyMaintenanceFee, interestRate, creditLimit);
            return creditAccountRepository.save(newAccount);
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The first owner identifier is not correct. " +
                    "Please introduce a valid identifier");
        }
    }
}
