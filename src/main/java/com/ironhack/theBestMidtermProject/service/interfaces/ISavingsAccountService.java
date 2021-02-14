package com.ironhack.theBestMidtermProject.service.interfaces;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;

public interface ISavingsAccountService {
    SavingsAccount checkAccount( Long accountId, Long userId);
    SavingsAccount createSavingsAccount(Long userId, SavingsAcDTO savingsAcDTO);
    SavingsAccount addAmount(Long accountId, Money amount);
    SavingsAccount subtractAmount(Long accountId, Money amount);
    void applyInterest(Long accountId);
}
