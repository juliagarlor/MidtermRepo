package com.ironhack.theBestMidtermProject.service.interfaces;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;

public interface IStudentAccountService {
    StudentCheckingAccount checkAccount( Long accountId, Long userId);
    StudentCheckingAccount addAmount(Long accountId, Money amount);
    StudentCheckingAccount subtractAmount(Long accountId, Money amount);
}
