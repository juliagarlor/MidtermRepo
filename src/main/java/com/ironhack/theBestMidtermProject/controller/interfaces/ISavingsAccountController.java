package com.ironhack.theBestMidtermProject.controller.interfaces;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;

public interface ISavingsAccountController {
    SavingsAccount createSavingsAccount(long userId, SavingsAcDTO savingsAcDTO);
}
