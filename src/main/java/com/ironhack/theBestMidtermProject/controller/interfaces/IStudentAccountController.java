package com.ironhack.theBestMidtermProject.controller.interfaces;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import org.springframework.security.core.*;

public interface IStudentAccountController {
    StudentCheckingAccount checkAccount(long accountId, Authentication authentication);
    StudentCheckingAccount addAmount(long accountId, Money amount);
    StudentCheckingAccount subtractAmount(long accountId, Money amount);
}
