package com.ironhack.theBestMidtermProject.controller.impl;

import com.ironhack.theBestMidtermProject.controller.interfaces.*;
import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.model.users.*;
import com.ironhack.theBestMidtermProject.service.impl.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.security.core.parameters.*;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
public class UserController implements IUserController {

    @Autowired
    private UserService userService;

//    A user may access to a view of his or her accounts:
    @GetMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getUser(@PathVariable long id) {
        return userService.getUser(id);
    }

    @GetMapping("/login/{userId}/{accountId}")
    @ResponseStatus(HttpStatus.OK)
    public Account login(@PathVariable long userId, @PathVariable long accountId, @RequestParam Optional<String> password) {
        return userService.login(userId, accountId, password);
    }

    @GetMapping("/logout/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public String logout(@PathVariable long userId){
        return userService.logout(userId);
    }

}
