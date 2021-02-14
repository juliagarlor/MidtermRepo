package com.ironhack.theBestMidtermProject.controller.impl;

import com.ironhack.theBestMidtermProject.controller.interfaces.*;
import com.ironhack.theBestMidtermProject.model.accounts.*;
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
public class CreditCardController implements ICreditCardController {
    @Autowired
    private ICreditCardService iCreditCardService;

    @GetMapping("/check/credit-card/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public CreditCardAccount checkAccount(@PathVariable long accountId, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        String userId = customUserDetails.getUsername();
        return iCreditCardService.checkAccount(accountId, userId);
    }

    @PostMapping("/new/credit-account/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCardAccount createCreditAccount(@PathVariable long userId, @RequestBody @Valid CreditAcDTO creditAcDTO){
        return iCreditCardService.createCreditAccount(userId, creditAcDTO);
    }

    @PatchMapping("/admin/credit-card/{accountId}/increaseBalance")
    @ResponseStatus(HttpStatus.OK)
    public CreditCardAccount addAmount(@PathVariable long accountId, @RequestBody Money amount){
        return iCreditCardService.addAmount(accountId, amount);
    }

    @PatchMapping("/admin/credit-card/{accountId}/decreaseBalance")
    @ResponseStatus(HttpStatus.OK)
    public CreditCardAccount subtractAmount(@PathVariable long accountId, @RequestBody Money amount){
        return iCreditCardService.subtractAmount(accountId, amount);
    }
}
