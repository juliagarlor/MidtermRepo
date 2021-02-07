package com.ironhack.theBestMidtermProject.service.interfaces;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;

public interface IAdminService {
    Account addAmount(long accountId, Money amount);
    Account subtractAmount(long accountId, Money amount);
    Account createCheckAccount(long userId, CheckingAcDTO checkingAcDTO);
}
