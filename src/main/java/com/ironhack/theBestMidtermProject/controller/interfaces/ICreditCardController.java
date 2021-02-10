package com.ironhack.theBestMidtermProject.controller.interfaces;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;
import org.springframework.security.core.*;

public interface ICreditCardController {
    CreditCardAccount checkAccount(long accountId, Authentication authentication);
    CreditCardAccount createCreditAccount(long userId, CreditAcDTO creditAcDTO);
    CreditCardAccount addAmount(long accountId, Money amount);
    CreditCardAccount subtractAmount(long accountId, Money amount);
}
