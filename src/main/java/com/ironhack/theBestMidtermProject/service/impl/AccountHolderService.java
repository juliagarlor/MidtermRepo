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
    private AdminRepository adminRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private AccountHolderRepository accountHolderRepository;

    private Transformer transformer = new Transformer();

    public Money checkBalance(Long accountId, Long userId) {
//        Looking for the account
        Optional<Account> account = accountRepository.findById(accountId);

        if (account.isPresent()){
//            If an account with this accountId exists, we store it in the variable output
            Account output = account.get();

//            Checking if the logged id is either the primaryOwner, the secondaryOwner or an admin
            Optional<Admin> admin = adminRepository.findById(userId);

//            Just in case it is not an admin, we take the first owner id
            Long primaryOwnerId = output.getPrimaryOwner().getId();

            if (admin.isPresent()){
//                if it's an admin, we directly return the balance
                return output.getBalance();
            }
//            if the logging id is not the primaryOwners and secondaryOwner is not null
            else if (!userId.equals(primaryOwnerId) && output.getSecondaryOwner() != null){
//                Take the id of the secondary owner and compare it with the userId
                Long secondaryOwnerId = output.getSecondaryOwner().getId();
                if (userId.equals(secondaryOwnerId)){
//                    if it's the secondary owner, return the balance
                    return output.getBalance();
                }else {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to check this data");
                }
            }
//            if the logging id is not the primaryOwner and the secondary id is null
            else if (!userId.equals(primaryOwnerId)){
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You are not authorized to check this data");
            }
//            and if the userId is the primary owner return the balance
            return output.getBalance();
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This id does not belong to any of our accounts. " +
                    "Please, introduce a valid id.");
        }
    }

    public AccountHolder createAccountHolder(AccountHolderDTO accountHolderDTO){

//        Taking everything out of the DTO
        Name name = transformer.assembleName(accountHolderDTO.getNameDTO());
        int age = accountHolderDTO.getAge();
        LocalDateTime dateOfBirth = accountHolderDTO.getDateOfBirth();
        Address primaryAddress = transformer.assembleAddress(accountHolderDTO.getPrimaryAddressDTO());
        Address mailingAddress = (accountHolderDTO.getMailingAddressDTO() != null) ?
                transformer.assembleAddress(accountHolderDTO.getMailingAddressDTO()) : null;
        String password = accountHolderDTO.getPassword();
//        Instantiating an empty set of roles
        Set<Role> roles = new HashSet<>();

//        Setting everything inside the new accounHolder
        AccountHolder newAccountHolder = new AccountHolder(name, password, age, roles, dateOfBirth, primaryAddress, mailingAddress);

//        Setting the role to ACCOUNT_HOLDER and pointing to this new accountHolder
        roles.add(new Role("ACCOUNT_HOLDER", newAccountHolder));
        newAccountHolder.setRoles(roles);

//        Saving into the database and return it
        return accountHolderRepository.save(newAccountHolder);
    }
}
