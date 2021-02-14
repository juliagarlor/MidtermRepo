package com.ironhack.theBestMidtermProject.controller.interfaces;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.security.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;

public interface ICheckingAccountController {
    CheckingAccount checkAccount(Long accountId, CustomUserDetails customUserDetails);
    CheckingAccount addAmount(Long accountId, Money amount);
    CheckingAccount subtractAmount(Long accountId, Money amount);
}
