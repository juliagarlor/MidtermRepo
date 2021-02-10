package com.ironhack.theBestMidtermProject.controller.interfaces;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;
import org.springframework.security.core.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;

public interface ISavingsAccountController {
    SavingsAccount checkAccount(long accountId, Authentication authentication);
    SavingsAccount createSavingsAccount(long userId, SavingsAcDTO savingsAcDTO);
    SavingsAccount addAmount(long accountId, Money amount);
    SavingsAccount subtractAmount(long accountId, Money amount);
}
