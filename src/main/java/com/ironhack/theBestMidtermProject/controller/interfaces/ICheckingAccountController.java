package com.ironhack.theBestMidtermProject.controller.interfaces;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import org.springframework.security.core.*;

public interface ICheckingAccountController {
    CheckingAccount checkAccount(long accountId, Authentication authentication);
    CheckingAccount addAmount(long accountId, Money amount);
    CheckingAccount subtractAmount(long accountId, Money amount);
}
