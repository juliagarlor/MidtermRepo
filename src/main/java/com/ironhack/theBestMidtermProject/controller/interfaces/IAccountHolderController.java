package com.ironhack.theBestMidtermProject.controller.interfaces;

import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

public interface IAccountHolderController {
    Money checkBalance(long accountId);
//    Money transferAmount(NameDTO nameDTO, long targetId, long emisorId, Money amount);
}
