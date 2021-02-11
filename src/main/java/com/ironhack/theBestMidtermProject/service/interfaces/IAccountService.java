package com.ironhack.theBestMidtermProject.service.interfaces;

import com.ironhack.theBestMidtermProject.model.*;
import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.security.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;

public interface IAccountService {
    Account createCheckAccount(long userId, CheckingAcDTO checkingAcDTO);
    Transactions transfer(long emisorId, TransactionsDTO transactionsDTO);
    Transactions transferWithThirdParty(String hashedKey, String accountSecretKey, long thirdPartyId, TransactionsDTO transactionsDTO);
}
