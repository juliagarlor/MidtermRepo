package com.ironhack.theBestMidtermProject.controller.impl;

import com.ironhack.theBestMidtermProject.controller.interfaces.*;
import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.service.interfaces.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;

@RestController
public class AccountController implements IAccountController {

    @Autowired
    private IAccountService iAccountService;

    @PostMapping("/new/checking-account/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createCheckAccount(@PathVariable long userId, @RequestBody @Valid CheckingAcDTO checkingAcDTO){
        return iAccountService.createCheckAccount(userId, checkingAcDTO);
    }

    @PatchMapping("/increaseBalance/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public Account addAmount(@PathVariable long accountId, @RequestBody Money amount){
        return iAccountService.addAmount(accountId, amount);
    }

    @PatchMapping("/decreaseBalance/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public Account subtractAmount(@PathVariable long accountId, @RequestBody Money amount){
        return iAccountService.subtractAmount(accountId, amount);
    }
}
