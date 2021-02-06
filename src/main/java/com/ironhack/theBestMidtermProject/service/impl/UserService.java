package com.ironhack.theBestMidtermProject.service.impl;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.model.users.*;
import com.ironhack.theBestMidtermProject.repository.users.*;
import com.ironhack.theBestMidtermProject.service.interfaces.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.server.*;

import java.util.*;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    public User getUser(long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()){
            return user.get();
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This id does not belong to any of our clients. " +
                    "Please, intruduce a valid id.");
        }
    }

    public Account login(long userId, long accountId, Optional<String> password) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()){
            User validUser = user.get();
            return checkValidAccess(accountId, validUser, password, true);
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This id does not belong to any of our clients. " +
                    "Please, intruduce a valid id.");
        }
    }

    public Account checkValidAccess(Long id, User user, Optional<String> password, boolean firstRound){
        Map<Long, Account> accounts;

        if (firstRound){
            accounts = user.getPrincipalAccounts();
        } else {
            accounts = user.getSecondaryAccounts();
        }

        Set<Long> keys = accounts.keySet();
        if (keys.contains(id)){
            Account account = accounts.get(id);
            if (account.getClass() != CreditCardAccount.class){
                if (password.isPresent()){
                    if (!account.checkPassword(password.get())){
                        throw new IllegalArgumentException("The password is incorrect.");
                    }
                }else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please, " +
                            "introduce the password of this account.");
                }
            }
            return account;
        } else {
            if (firstRound){
                checkValidAccess(id, user, password, false);
            }else {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The account number " + id + " does not belong to "
                        + user.getName() + ".");
            }
        }
        return null;
    }
}
