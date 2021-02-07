package com.ironhack.theBestMidtermProject.service.interfaces;

import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;
import org.springframework.web.bind.annotation.*;

public interface IAccountHolderService {
    Money checkBalance(long accountId);
    Money transferAmount(NameDTO nameDTO, long targetId, Money amount);
}
