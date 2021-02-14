package com.ironhack.theBestMidtermProject.service.interfaces;

import com.ironhack.theBestMidtermProject.model.users.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

public interface IAccountHolderService {
    Money checkBalance(long accountId, Long userId);
    AccountHolder createAccountHolder(AccountHolderDTO accountHolderDTO);
}
