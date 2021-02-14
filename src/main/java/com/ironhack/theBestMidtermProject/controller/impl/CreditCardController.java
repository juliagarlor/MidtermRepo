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

/*    checkAccount takes an account id, the details from the login of the current session and an inminent feeling of sadness,
    and should return the details of the account. This route can be only used by admins and the primary or secondary
    owner of the referred account.*/

    @GetMapping("/check/credit-card/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public CreditCardAccount checkAccount(@PathVariable Long accountId, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        String userId = customUserDetails.getUsername();
        return iCreditCardService.checkAccount(accountId, userId);
    }

/*    createCreditAccount returns a new CreditCardAccount belonging to the account holder whose user id matches with the
    one introduced in the url. This route can only be used by an admin, and ask for the information of the account to
    be created. */

    @PostMapping("/new/credit-account/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCardAccount createCreditAccount(@PathVariable Long userId, @RequestBody @Valid CreditAcDTO creditAcDTO){
        return iCreditCardService.createCreditAccount(userId, creditAcDTO);
    }

/*    addAmount takes an account id and an amount of money, and should return the details of the account with an updated
      balance incremented by the previous amount. This route can be only used by admins.*/

    @PatchMapping("/admin/credit-card/{accountId}/increaseBalance")
    @ResponseStatus(HttpStatus.OK)
    public CreditCardAccount addAmount(@PathVariable Long accountId, @RequestBody Money amount){
        return iCreditCardService.addAmount(accountId, amount);
    }

/*    subtractAmount takes an account id and an amount of money, and should return the details of the account with an
      updated balance decreased by the previous amount. This route can be only used by admins.*/

    @PatchMapping("/admin/credit-card/{accountId}/decreaseBalance")
    @ResponseStatus(HttpStatus.OK)
    public CreditCardAccount subtractAmount(@PathVariable Long accountId, @RequestBody Money amount){
        return iCreditCardService.subtractAmount(accountId, amount);
    }
}
