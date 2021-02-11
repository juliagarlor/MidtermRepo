package com.ironhack.theBestMidtermProject.controller.impl;

import com.ironhack.theBestMidtermProject.controller.interfaces.*;
import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.model.users.*;
import com.ironhack.theBestMidtermProject.repository.accounts.*;
import com.ironhack.theBestMidtermProject.repository.users.*;
import com.ironhack.theBestMidtermProject.security.*;
import com.ironhack.theBestMidtermProject.service.interfaces.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.core.annotation.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.security.*;

@RestController
public class AccountHolderController implements IAccountHolderController {

    @Autowired
    private IAccountHolderService iAccountHolderService;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @GetMapping("/balance/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public Money checkBalance(@PathVariable long accountId, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        Long userId = Long.valueOf(customUserDetails.getUsername());
        return iAccountHolderService.checkBalance(accountId, userId);
    }

    @PostMapping("/register/accountHolder")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder createAccountHolder(@RequestBody @Valid AccountHolderDTO accountHolderDTO){
        return iAccountHolderService.createAccountHolder(accountHolderDTO);
    }
}
