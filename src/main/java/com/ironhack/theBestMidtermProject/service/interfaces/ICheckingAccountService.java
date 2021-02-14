package com.ironhack.theBestMidtermProject.service.interfaces;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;

public interface ICheckingAccountService {
    CheckingAccount checkAccount( Long accountId, Long userId);
    CheckingAccount addAmount(Long accountId, Money amount);
    CheckingAccount subtractAmount(Long accountId, Money amount);
}
