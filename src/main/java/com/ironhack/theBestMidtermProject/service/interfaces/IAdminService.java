package com.ironhack.theBestMidtermProject.service.interfaces;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.model.users.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;

public interface IAdminService {
    Admin createAdmin(AdminDTO adminDTO);
    Account addAmount(long accountId, Money amount);
    Account subtractAmount(long accountId, Money amount);
    Account createCheckAccount(long userId, CheckingAcDTO checkingAcDTO);
    Account createSavingsAccount(long userId, SavingsAcDTO savingsAcDTO);
    Account createCreditAccount(long userId, CreditAcDTO creditAcDTO);
}
