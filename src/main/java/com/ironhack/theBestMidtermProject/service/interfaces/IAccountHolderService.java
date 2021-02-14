package com.ironhack.theBestMidtermProject.service.interfaces;

import com.ironhack.theBestMidtermProject.model.users.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;

public interface IAccountHolderService {
    Money checkBalance(Long accountId, Long userId);
    AccountHolder createAccountHolder(AccountHolderDTO accountHolderDTO);
}
