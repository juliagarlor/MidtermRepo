package com.ironhack.theBestMidtermProject.service.interfaces;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;

public interface ICheckingAccountService {
    CheckingAccount checkAccount( long accountId, String userId);
    CheckingAccount addAmount(long accountId, Money amount);
    CheckingAccount subtractAmount(long accountId, Money amount);
}
