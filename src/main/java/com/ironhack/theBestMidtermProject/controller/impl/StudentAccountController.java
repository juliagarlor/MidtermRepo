package com.ironhack.theBestMidtermProject.controller.impl;

import com.ironhack.theBestMidtermProject.controller.interfaces.*;
import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.security.*;
import com.ironhack.theBestMidtermProject.service.interfaces.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.core.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentAccountController implements IStudentAccountController {

    @Autowired
    private IStudentAccountService iStudentAccountService;

/*    checkAccount takes an account id, the details from the login of the current session and an inminent feeling of sadness,
    and should return the details of the account. This route can be only used by admins and the primary or secondary
    owner of the referred account.*/

    @GetMapping("/check/student-account/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public StudentCheckingAccount checkAccount(@PathVariable Long accountId,
                                               @AuthenticationPrincipal CustomUserDetails customUserDetails){
        Long userId = Long.parseLong(customUserDetails.getUsername());
        return iStudentAccountService.checkAccount(accountId, userId);
    }

/*    addAmount takes an account id and an amount of money, and should return the details of the account with an updated
      balance incremented by the previous amount. This route can be only used by admins.*/

    @PatchMapping("/admin/student-account/{accountId}/increaseBalance")
    @ResponseStatus(HttpStatus.OK)
    public StudentCheckingAccount addAmount(@PathVariable Long accountId, @RequestBody Money amount){
        return iStudentAccountService.addAmount(accountId, amount);
    }

/*    subtractAmount takes an account id and an amount of money, and should return the details of the account with an
      updated balance decreased by the previous amount. This route can be only used by admins.*/

    @PatchMapping("/admin/student-account/{accountId}/decreaseBalance")
    @ResponseStatus(HttpStatus.OK)
    public StudentCheckingAccount subtractAmount(@PathVariable Long accountId, @RequestBody Money amount){
        return iStudentAccountService.subtractAmount(accountId, amount);
    }
}
