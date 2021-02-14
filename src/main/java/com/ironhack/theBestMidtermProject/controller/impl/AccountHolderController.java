package com.ironhack.theBestMidtermProject.controller.impl;

import com.ironhack.theBestMidtermProject.controller.interfaces.*;
import com.ironhack.theBestMidtermProject.model.users.*;
import com.ironhack.theBestMidtermProject.security.*;
import com.ironhack.theBestMidtermProject.service.interfaces.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.core.annotation.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;

@RestController
public class AccountHolderController implements IAccountHolderController {

    @Autowired
    private IAccountHolderService iAccountHolderService;

/*    checkBalance takes an accountId and the credentials used in the login to return the balance of the searched account.
    This route can be only used by admins and by the account holder of the referenced account, both primary or secondary
    owners*/

    @GetMapping("/balance/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public Money checkBalance(@PathVariable Long accountId, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        Long userId = Long.valueOf(customUserDetails.getUsername());
        return iAccountHolderService.checkBalance(accountId, userId);
    }

/*    createAccountHolder takes the info of a new account holder and return this brand new AccountHolder. This route can
    be used by anyone without needing authentication.*/

    @PostMapping("/register/accountHolder")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder createAccountHolder(@RequestBody @Valid AccountHolderDTO accountHolderDTO){
        return iAccountHolderService.createAccountHolder(accountHolderDTO);
    }
}
