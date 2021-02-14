package com.ironhack.theBestMidtermProject.controller.interfaces;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.security.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;

public interface ISavingsAccountController {
    SavingsAccount checkAccount(Long accountId, CustomUserDetails customUserDetails);
    SavingsAccount createSavingsAccount(Long userId, SavingsAcDTO savingsAcDTO);
    SavingsAccount addAmount(Long accountId, Money amount);
    SavingsAccount subtractAmount(Long accountId, Money amount);
}
