package com.ironhack.theBestMidtermProject.controller.interfaces;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;

public interface IStudentAccountController {
    StudentCheckingAccount addAmount(long accountId, Money amount);
    StudentCheckingAccount subtractAmount(long accountId, Money amount);
}
