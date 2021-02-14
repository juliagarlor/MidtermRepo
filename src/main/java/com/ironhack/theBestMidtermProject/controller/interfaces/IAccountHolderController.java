package com.ironhack.theBestMidtermProject.controller.interfaces;

import com.ironhack.theBestMidtermProject.model.users.*;
import com.ironhack.theBestMidtermProject.security.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;

public interface IAccountHolderController {
    Money checkBalance(Long accountId, CustomUserDetails customUserDetails);
    AccountHolder createAccountHolder(AccountHolderDTO accountHolderDTO);
}
