package com.ironhack.theBestMidtermProject.controller.impl;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.security.*;
import com.ironhack.theBestMidtermProject.service.interfaces.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.core.*;
import org.springframework.security.core.annotation.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class StudentAccountController {

    @Autowired
    private IStudentAccountService iStudentAccountService;

    @GetMapping("/check/student-account/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public StudentCheckingAccount checkAccount(@PathVariable long accountId,
                                               @AuthenticationPrincipal CustomUserDetails customUserDetails){
        String userId = customUserDetails.getUsername();
        return iStudentAccountService.checkAccount(accountId, userId);
    }

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
