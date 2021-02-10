package com.ironhack.theBestMidtermProject.controller.interfaces;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;

public interface ICheckingAccountController {
    CheckingAccount addAmount(long accountId, Money amount);
    CheckingAccount subtractAmount(long accountId, Money amount);
}
