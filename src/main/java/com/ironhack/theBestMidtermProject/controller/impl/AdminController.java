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

//todo SI USO DTOS PARA METER DATOS, EL ORDEN EN EL QUE SE ESCRIBAN LOS PARÁMETROS EN EL BODY ES IMPORTANTE?

@RestController
public class AdminController implements IAdminController {
    @Autowired
    private IAdminService iAdminService;

    @PatchMapping("/increaseBalance/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public Account addAmount(@PathVariable long accountId, @RequestBody Money amount){
        return iAdminService.addAmount(accountId, amount);
    }

    @PatchMapping("/decreaseBalance/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public Account subtractAmount(@PathVariable long accountId, @RequestBody Money amount){
        return iAdminService.subtractAmount(accountId, amount);
    }

//    When creating a new account they can create Checking, Savings, or CreditCard Accounts.
    @PostMapping("/new/checking-account/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createCheckAccount(@PathVariable long userId, @RequestBody @Valid CheckingAcDTO checkingAcDTO){
        return iAdminService.createCheckAccount(userId, checkingAcDTO);
    }

    @PostMapping("/new/savings-account/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createSavingsAccount(@PathVariable long userId, @RequestBody @Valid SavingsAcDTO savingsAcDTO){
        return iAdminService.createSavingsAccount(userId, savingsAcDTO);
    }

    @PostMapping("/new/credit-account/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public Account createCreditAccount(@PathVariable long userId, @RequestBody @Valid CreditAcDTO creditAcDTO){
        return iAdminService.createCreditAccount(userId, creditAcDTO);
    }
}
