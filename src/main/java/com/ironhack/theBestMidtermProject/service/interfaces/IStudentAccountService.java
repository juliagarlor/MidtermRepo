package com.ironhack.theBestMidtermProject.service.interfaces;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;

public interface IStudentAccountService {
    StudentCheckingAccount addAmount(long accountId, Money amount);
    StudentCheckingAccount subtractAmount(long accountId, Money amount);
}
