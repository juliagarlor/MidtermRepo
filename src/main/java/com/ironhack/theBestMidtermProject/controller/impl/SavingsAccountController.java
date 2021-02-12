package com.ironhack.theBestMidtermProject.controller.impl;

import com.ironhack.theBestMidtermProject.controller.interfaces.*;
import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.security.*;
import com.ironhack.theBestMidtermProject.service.interfaces.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.core.*;
import org.springframework.security.core.annotation.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;

@RestController
public class SavingsAccountController implements ISavingsAccountController {

    @Autowired
    private ISavingsAccountService iSavingsAccountService;

    @GetMapping("/check/savings-account/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public SavingsAccount checkAccount(@PathVariable long accountId, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        String userId = customUserDetails.getUsername();
        return iSavingsAccountService.checkAccount(accountId, userId);
    }

    @PostMapping("/new/savings-account/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public SavingsAccount createSavingsAccount(@PathVariable long userId, @RequestBody @Valid SavingsAcDTO savingsAcDTO){
        return iSavingsAccountService.createSavingsAccount(userId, savingsAcDTO);
    }

    @PatchMapping("/admin/savings-account/{accountId}/increaseBalance")
    @ResponseStatus(HttpStatus.OK)
    public SavingsAccount addAmount(@PathVariable long accountId, @RequestBody Money amount){
        return iSavingsAccountService.addAmount(accountId, amount);
    }

    @PatchMapping("/admin/savings-account/{accountId}/decreaseBalance")
    @ResponseStatus(HttpStatus.OK)
    public SavingsAccount subtractAmount(@PathVariable long accountId, @RequestBody Money amount){
        return iSavingsAccountService.subtractAmount(accountId, amount);
    }
}
