package com.ironhack.theBestMidtermProject.controller.interfaces;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;

public interface ICreditCardController {
    CreditCardAccount createCreditAccount(long userId, CreditAcDTO creditAcDTO);
}
