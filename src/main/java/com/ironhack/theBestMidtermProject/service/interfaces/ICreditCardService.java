package com.ironhack.theBestMidtermProject.service.interfaces;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;
import org.springframework.web.bind.annotation.*;

public interface ICreditCardService {
    CreditCardAccount checkAccount( long accountId, String userId);
    CreditCardAccount createCreditAccount(long userId, CreditAcDTO creditAcDTO);
    CreditCardAccount addAmount(long accountId, Money amount);
    CreditCardAccount subtractAmount(long accountId, Money amount);
    Money applyInterest(long accountId);
}
