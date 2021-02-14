package com.ironhack.theBestMidtermProject.controller.interfaces;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.security.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;

public interface IStudentAccountController {
    StudentCheckingAccount checkAccount(Long accountId, CustomUserDetails customUserDetails);
    StudentCheckingAccount addAmount(Long accountId, Money amount);
    StudentCheckingAccount subtractAmount(Long accountId, Money amount);
}
