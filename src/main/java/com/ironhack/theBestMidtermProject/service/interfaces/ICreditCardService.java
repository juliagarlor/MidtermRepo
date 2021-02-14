package com.ironhack.theBestMidtermProject.service.interfaces;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;

public interface ICreditCardService {
    CreditCardAccount checkAccount( Long accountId, Long userId);
    CreditCardAccount createCreditAccount(Long userId, CreditAcDTO creditAcDTO);
    CreditCardAccount addAmount(Long accountId, Money amount);
    CreditCardAccount subtractAmount(Long accountId, Money amount);
    Money applyInterest(Long accountId);
}
