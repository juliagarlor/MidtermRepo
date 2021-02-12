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

    private Transformer transformer = new Transformer();

    public Money checkBalance(long accountId, Long userId) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isPresent()){
            Account output = account.get();
//            Checking if the logged id is either the primaryOwner, the secondaryOwner or an admin

            Optional<User> admin = userRepository.findById(userId);
            boolean isAdmin = admin.get().getRoles().stream().anyMatch(x ->x.getName().equals("ADMIN"));

            Long primaryOwnerId = output.getPrimaryOwner().getId();

            if (isAdmin){
                return output.getBalance();
            }
//            if the logging id is not the primaryOwners and secondaryOwner is not null
            else if (!userId.equals(primaryOwnerId) && output.getSecondaryOwner() != null){
                Long secondaryOwnerId = output.getSecondaryOwner().getId();
                if (userId.equals(secondaryOwnerId)){
                    return output.getBalance();
                }else {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to check this data");
                }
            }
//            if the logging id is not the primaryOwner and the secondary id is null
            else if (!userId.equals(primaryOwnerId)){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to check this data");
            }
            return account.get().getBalance();
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This id does not belong to any of our accounts. " +
                    "Please, intruduce a valid id.");
        }
    }

    public AccountHolder createAccountHolder(AccountHolderDTO accountHolderDTO){

        Name name = transformer.assembleName(accountHolderDTO.getNameDTO());
        int age = accountHolderDTO.getAge();
        LocalDateTime dateOfBirth = accountHolderDTO.getDateOfBirth();
        Address primaryAddress = transformer.assembleAddress(accountHolderDTO.getPrimaryAddressDTO());
        Address mailingAddress = (accountHolderDTO.getMailingAddressDTO() != null) ?
                transformer.assembleAddress(accountHolderDTO.getMailingAddressDTO()) : null;
        String password = accountHolderDTO.getPassword();
        Set<Role> roles = new HashSet<>();

        AccountHolder newAccountHolder = new AccountHolder(name, password, age, roles, dateOfBirth, primaryAddress, mailingAddress);

        roles.add(new Role("ACCOUNT_HOLDER", newAccountHolder));
        newAccountHolder.setRoles(roles);
        return accountHolderRepository.save(newAccountHolder);
    }
}
