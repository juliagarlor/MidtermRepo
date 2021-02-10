package com.ironhack.theBestMidtermProject.controller.interfaces;

import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;
import org.springframework.security.core.*;
import org.springframework.web.bind.annotation.*;

import java.security.*;
import java.util.*;

public interface IAccountHolderController {
    Money checkBalance(long accountId, Principal principal);
//    Money transferAmount(NameDTO nameDTO, long targetId, long emisorId, Money amount);
}
