package com.ironhack.theBestMidtermProject.controller.impl;

import com.ironhack.theBestMidtermProject.controller.interfaces.*;
import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.service.interfaces.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;

@RestController
public class SavingsAccountController implements ISavingsAccountController {

    @Autowired
    private ISavingsAccountService iSavingsAccountService;

    @PostMapping("/new/savings-account/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public SavingsAccount createSavingsAccount(@PathVariable long userId, @RequestBody @Valid SavingsAcDTO savingsAcDTO){
        return iSavingsAccountService.createSavingsAccount(userId, savingsAcDTO);
    }
}
