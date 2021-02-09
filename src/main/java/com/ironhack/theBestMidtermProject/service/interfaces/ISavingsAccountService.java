package com.ironhack.theBestMidtermProject.service.interfaces;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;

public interface ISavingsAccountService {
    SavingsAccount createSavingsAccount(long userId, SavingsAcDTO savingsAcDTO);
}
