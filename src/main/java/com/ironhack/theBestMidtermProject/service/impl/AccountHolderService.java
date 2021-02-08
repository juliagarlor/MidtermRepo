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

import javax.validation.*;
import java.time.*;
import java.util.*;

@Service
public class AccountHolderService implements IAccountHolderService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountHolderRepository accountHolderRepository;

    private Ensambler ensambler;

    public Money checkBalance(long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isPresent()){
            return account.get().getBalance();
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This id does not belong to any of our accounts. " +
                    "Please, intruduce a valid id.");
        }
    }

    public AccountHolder createAccountHolder(AccountHolderDTO accountHolderDTO){

        Name name = ensambler.ensambleName(accountHolderDTO.getNameDTO());
        int age = accountHolderDTO.getAge();
        LocalDateTime dateOfBirth = accountHolderDTO.getDateOfBirth();
        Address primaryAddress = ensambler.ensambleAddress(accountHolderDTO.getPrimaryAddressDTO());
        Address mailingAddress = ensambler.ensambleAddress(accountHolderDTO.getMailingAddressDTO());
        Set<Role> roles = new HashSet<>();

        AccountHolder newAccountHolder = new AccountHolder(name, age, roles, dateOfBirth, primaryAddress, mailingAddress);

        roles.add(new Role("USER", newAccountHolder));
        newAccountHolder.setRoles(roles);
        return accountHolderRepository.save(newAccountHolder);
    }

////    TODO NO VOY A COMPROBAR SI LA CANTIDAD QUE ME DAN ES POSITIVA O NEGATIVA, PREGUNTA Y DESPUES LO ARREGLAMOS
////    CUANDO PUEDAS LIMPIA ESTO UN POCO:
//    public Money transferAmount(Name name, long targetId, long emisorId, Money amount) {
////        This way we check both that the emisor id is correct and that this account has enough balance
//        Money emisorBalance = checkBalance(emisorId);
//        Account emisorAccount = accountRepository.findById(emisorId).get();
//
//        if (emisorBalance.getAmount().compareTo(amount.getAmount()) != 0){
//            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "The balance of the emisor account is not enough for this transaction");
//        }
//
//        Optional<Account> receptorAccount = accountRepository.findById(targetId);
//
////        if (receptorAccount.isPresent()){
//////            lets check whether this is the account we are looking for
////            boolean matchingNameAndId = userRepository.findByNameAndAccountsIdIs(name, targetId).isPresent();
////            if (matchingNameAndId){
//////                Subtract from the emisor balance:
////                emisorBalance = new Money(emisorAccount.getBalance().decreaseAmount(amount));
////                emisorAccount.setBalance(emisorBalance);
////                accountRepository.save(emisorAccount);
//////                Add it to the receptor balance:
////                Money receptorBalance = new Money(amount.increaseAmount(receptorAccount.get().getBalance()));
////                receptorAccount.get().setBalance(receptorBalance);
////                accountRepository.save(receptorAccount.get());
////            }else {
////                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Receptor's name and account id do not match. " +
////                        "Please, intruduce valid values.");
////            }
////        }else {
////            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found receptor account id. " +
////                    "Please, intruduce a valid id.");
////        }
//
//        return emisorBalance;
//    }
}
