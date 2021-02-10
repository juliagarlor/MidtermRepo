package com.ironhack.theBestMidtermProject.controller.interfaces;

import com.ironhack.theBestMidtermProject.model.*;
import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;
import org.springframework.security.core.annotation.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.security.*;

public interface IAccountController {
    Account createCheckAccount(long userId, CheckingAcDTO checkingAcDTO);
    Transactions transfer(Principal principal, TransactionsDTO transactionsDTO);
}
