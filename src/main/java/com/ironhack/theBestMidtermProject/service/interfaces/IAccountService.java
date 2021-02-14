package com.ironhack.theBestMidtermProject.service.interfaces;

import com.ironhack.theBestMidtermProject.model.*;
import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;

public interface IAccountService {
    Account createCheckAccount(Long userId, CheckingAcDTO checkingAcDTO);
    Transactions transfer(Long emisorId, TransactionsDTO transactionsDTO);
    Transactions transferWithThirdParty(String hashedKey, String accountSecretKey, Long thirdPartyId, TransactionsDTO transactionsDTO);
    void castToOtherThanCreditAndCheckSecretKey(Account account, String secretKey);
    void checkUnderMinimum(Account account);
}
