package com.ironhack.theBestMidtermProject.controller.impl;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.service.interfaces.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentAccountController {

    @Autowired
    private IStudentAccountService iStudentAccountService;

    @PatchMapping("/admin/student-account/{accountId}/increaseBalance")
    @ResponseStatus(HttpStatus.OK)
    public StudentCheckingAccount addAmount(@PathVariable long accountId, @RequestBody Money amount){
        return iStudentAccountService.addAmount(accountId, amount);
    }

    @PatchMapping("/admin/student-account/{accountId}/decreaseBalance")
    @ResponseStatus(HttpStatus.OK)
    public StudentCheckingAccount subtractAmount(@PathVariable long accountId, @RequestBody Money amount){
        return iStudentAccountService.subtractAmount(accountId, amount);
    }
}
