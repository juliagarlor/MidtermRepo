package com.ironhack.theBestMidtermProject.service.interfaces;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;

public interface ICreditCardService {
    CreditCardAccount createCreditAccount(long userId, CreditAcDTO creditAcDTO);
}
