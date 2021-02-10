package com.ironhack.theBestMidtermProject.service.interfaces;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import org.springframework.security.core.*;
import org.springframework.security.core.annotation.*;
import org.springframework.web.bind.annotation.*;

public interface IStudentAccountService {
    StudentCheckingAccount checkAccount( long accountId, String userId);
    StudentCheckingAccount addAmount(long accountId, Money amount);
    StudentCheckingAccount subtractAmount(long accountId, Money amount);
}
