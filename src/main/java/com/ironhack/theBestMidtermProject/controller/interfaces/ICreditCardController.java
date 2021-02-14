package com.ironhack.theBestMidtermProject.controller.interfaces;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.security.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;

public interface ICreditCardController {
    CreditCardAccount checkAccount(Long accountId, CustomUserDetails customUserDetails);
    CreditCardAccount createCreditAccount(Long userId, CreditAcDTO creditAcDTO);
    CreditCardAccount addAmount(Long accountId, Money amount);
    CreditCardAccount subtractAmount(Long accountId, Money amount);
}
