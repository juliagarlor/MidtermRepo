package com.ironhack.theBestMidtermProject.controller.interfaces;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.model.users.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

public interface IUserController {
    User getUser(long id);
    Account login(long userId, long accountId, Optional<String> password);
    public String logout(long userId);
}
