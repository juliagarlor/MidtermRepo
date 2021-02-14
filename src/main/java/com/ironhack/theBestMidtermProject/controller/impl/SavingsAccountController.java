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
public class SavingsAccountController implements ISavingsAccountController {

    @Autowired
    private ISavingsAccountService iSavingsAccountService;

/*    checkAccount takes an account id, the details from the login of the current session and an inminent feeling of sadness,
    and should return the details of the account. This route can be only used by admins and the primary or secondary
    owner of the referred account.*/

    @GetMapping("/check/savings-account/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public SavingsAccount checkAccount(@PathVariable Long accountId, @AuthenticationPrincipal CustomUserDetails customUserDetails){
        String userId = customUserDetails.getUsername();
        return iSavingsAccountService.checkAccount(accountId, userId);
    }

/*    createSavingsAccount returns a new SavingsAccount belonging to the account holder whose user id matches with the
    one introduced in the url. This route can only be used by an admin, and ask for the information of the account to
    be created. */

    @PostMapping("/new/savings-account/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public SavingsAccount createSavingsAccount(@PathVariable Long userId, @RequestBody @Valid SavingsAcDTO savingsAcDTO){
        return iSavingsAccountService.createSavingsAccount(userId, savingsAcDTO);
    }

/*    addAmount takes an account id and an amount of money, and should return the details of the account with an updated
      balance incremented by the previous amount. This route can be only used by admins.*/

    @PatchMapping("/admin/savings-account/{accountId}/increaseBalance")
    @ResponseStatus(HttpStatus.OK)
    public SavingsAccount addAmount(@PathVariable Long accountId, @RequestBody Money amount){
        return iSavingsAccountService.addAmount(accountId, amount);
    }

/*    subtractAmount takes an account id and an amount of money, and should return the details of the account with an
      updated balance decreased by the previous amount. This route can be only used by admins.*/

    @PatchMapping("/admin/savings-account/{accountId}/decreaseBalance")
    @ResponseStatus(HttpStatus.OK)
    public SavingsAccount subtractAmount(@PathVariable Long accountId, @RequestBody Money amount){
        return iSavingsAccountService.subtractAmount(accountId, amount);
    }
}
