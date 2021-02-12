package com.ironhack.theBestMidtermProject.controller.impl;

import com.ironhack.theBestMidtermProject.controller.interfaces.*;
import com.ironhack.theBestMidtermProject.model.*;
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
import java.security.*;

@RestController
public class AccountController implements IAccountController {

    @Autowired
    private IAccountService iAccountService;

    @PostMapping("/new/checking-account/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createCheckAccount(@PathVariable long userId, @RequestBody @Valid CheckingAcDTO checkingAcDTO){
        return iAccountService.createCheckAccount(userId, checkingAcDTO);
    }

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.CREATED)
    public Transactions transfer(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                 @RequestBody @Valid TransactionsDTO transactionsDTO){
        long emisorId = Long.parseLong(customUserDetails.getUsername());
        return iAccountService.transfer(emisorId, transactionsDTO);
    }

    @PostMapping("/transfer/{hashedKey}/{accountSecretKey}")
    @ResponseStatus(HttpStatus.CREATED)
    public Transactions transferWithThirdParty(@PathVariable String hashedKey, @PathVariable String accountSecretKey,
                                               @AuthenticationPrincipal CustomUserDetails customUserDetails,
                                               @RequestBody @Valid TransactionsDTO transactionsDTO){
        long emisorId = Long.parseLong(customUserDetails.getUsername());
        return iAccountService.transferWithThirdParty(hashedKey,  accountSecretKey, emisorId, transactionsDTO);
    }
}
