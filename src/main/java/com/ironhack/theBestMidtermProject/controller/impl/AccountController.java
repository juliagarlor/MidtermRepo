package com.ironhack.theBestMidtermProject.controller.impl;

import com.ironhack.theBestMidtermProject.controller.interfaces.*;
import com.ironhack.theBestMidtermProject.model.*;
import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.security.*;
import com.ironhack.theBestMidtermProject.service.interfaces.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.core.annotation.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;

@RestController
public class AccountController implements IAccountController {

    @Autowired
    private IAccountService iAccountService;

/*    createCheckAccount gives out either a CheckingAccount or a StudentCheckingAccount depending on the age of the
    account holder referenced by the userId path variable. This route can only be used by an admin, and ask for the
    information of the account to be created */

    @PostMapping("/new/checking-account/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createCheckAccount(@PathVariable Long userId, @RequestBody @Valid CheckingAcDTO checkingAcDTO){
        return iAccountService.createCheckAccount(userId, checkingAcDTO);
    }

/*    transfer takes the details from the login of the current session, and the data from a transaction. If possible,
    it returns the complete data of the performed transaction. This route can be only used by an account holder to send
    a transference to another account holder*/

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.CREATED)
    public Transactions transfer(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                 @RequestBody @Valid TransactionsDTO transactionsDTO){
        long emisorId = Long.parseLong(customUserDetails.getUsername());
        return iAccountService.transfer(emisorId, transactionsDTO);
    }

/*    transferWithThirdParty works in the same way as transfer, but this time, it is the third party who should login with
    its credentials, either for send or ask for a transference from any of our account holders. The third party should
    provide with its own hashedKey, and the accountSecretKey of the other account*/

    @PostMapping("/transfer/{hashedKey}/{accountSecretKey}")
    @ResponseStatus(HttpStatus.CREATED)
    public Transactions transferWithThirdParty(@PathVariable String hashedKey, @PathVariable String accountSecretKey,
                                               @AuthenticationPrincipal CustomUserDetails customUserDetails,
                                               @RequestBody @Valid TransactionsDTO transactionsDTO){
        long emisorId = Long.parseLong(customUserDetails.getUsername());
        return iAccountService.transferWithThirdParty(hashedKey,  accountSecretKey, emisorId, transactionsDTO);
    }
}
