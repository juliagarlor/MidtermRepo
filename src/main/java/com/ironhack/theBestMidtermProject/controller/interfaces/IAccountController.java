package com.ironhack.theBestMidtermProject.controller.interfaces;

import com.ironhack.theBestMidtermProject.model.*;
import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.security.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;

public interface IAccountController {
    Account createCheckAccount(Long userId, CheckingAcDTO checkingAcDTO);
    Transactions transfer(CustomUserDetails customUserDetails, TransactionsDTO transactionsDTO);
    Transactions transferWithThirdParty(String hashedKey, String accountSecretKey, CustomUserDetails customUserDetails, TransactionsDTO transactionsDTO);
}
