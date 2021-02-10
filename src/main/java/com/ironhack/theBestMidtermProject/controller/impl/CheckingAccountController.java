package com.ironhack.theBestMidtermProject.controller.impl;

import com.ironhack.theBestMidtermProject.controller.interfaces.*;
import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.service.interfaces.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class CheckingAccountController implements ICheckingAccountController {

    @Autowired
    private ICheckingAccountService iCheckingAccountService;

    @PatchMapping("/admin/checking-account/{accountId}/increaseBalance")
    @ResponseStatus(HttpStatus.OK)
    public CheckingAccount addAmount(@PathVariable long accountId, @RequestBody Money amount){
        return iCheckingAccountService.addAmount(accountId, amount);
    }

    @PatchMapping("/admin/checking-account/{accountId}/decreaseBalance")
    @ResponseStatus(HttpStatus.OK)
    public CheckingAccount subtractAmount(@PathVariable long accountId, @RequestBody Money amount){
        return iCheckingAccountService.subtractAmount(accountId, amount);
    }
}
