package com.ironhack.theBestMidtermProject.controller.interfaces;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;

public interface IAccountController {
    Account createCheckAccount(long userId, CheckingAcDTO checkingAcDTO);
    Account addAmount(long accountId, Money amount);
    Account subtractAmount(long accountId, Money amount);
}
