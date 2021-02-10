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
public class CreditCardController implements ICreditCardController {
    @Autowired
    private ICreditCardService iCreditCardService;

    @PostMapping("/new/credit-account/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCardAccount createCreditAccount(@PathVariable long userId, @RequestBody @Valid CreditAcDTO creditAcDTO){
        return iCreditCardService.createCreditAccount(userId, creditAcDTO);
//        return new CreditCardAccount();
    }
}
