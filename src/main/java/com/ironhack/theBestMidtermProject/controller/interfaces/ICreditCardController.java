package com.ironhack.theBestMidtermProject.controller.interfaces;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.security.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;
import org.springframework.security.core.*;

public interface ICreditCardController {
    CreditCardAccount checkAccount(long accountId, CustomUserDetails customUserDetails);
    CreditCardAccount createCreditAccount(long userId, CreditAcDTO creditAcDTO);
    CreditCardAccount addAmount(long accountId, Money amount);
    CreditCardAccount subtractAmount(long accountId, Money amount);
}
