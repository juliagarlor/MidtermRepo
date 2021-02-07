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
import org.springframework.web.server.*;

import java.util.*;

public class AccountHolderService implements IAccountHolderService {

    @Autowired
    private AccountRepository accountRepository;

    public Money checkBalance(long accountId) {
        Optional<Account> account = accountRepository.findById(accountId);
        if (account.isPresent()){
            return account.get().getBalance();
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This id does not belong to any of our accounts. " +
                    "Please, intruduce a valid id.");
        }
    }


//    todo ME QUEDO POR AQUIIIIIII
    public Money transferAmount(NameDTO nameDTO, long targetId, Money amount) {
//        nameDTO is the name of the transfering account's first or second owner
    }
}
