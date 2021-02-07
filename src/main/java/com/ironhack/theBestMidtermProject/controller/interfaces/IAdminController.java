package com.ironhack.theBestMidtermProject.controller.interfaces;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;
import org.springframework.web.bind.annotation.*;

public interface IAdminController {
    Account addAmount(long accountId, Money amount);
    Account subtractAmount(long accountId, Money amount);
    Account createCheckAccount(long userId, CheckingAcDTO checkingAcDTO);
}
