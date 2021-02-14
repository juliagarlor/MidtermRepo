package com.ironhack.theBestMidtermProject.controller.impl;

import com.ironhack.theBestMidtermProject.controller.interfaces.*;
import com.ironhack.theBestMidtermProject.model.users.*;
import com.ironhack.theBestMidtermProject.service.interfaces.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;

@RestController
public class AdminController implements IAdminController {

    @Autowired
    private IAdminService iAdminService;

/*    createAdmin takes the info of a new admin and return this brand new Admin. This route can be used by anyone
    without needing authentication.*/

    @PostMapping("/register/administrator")
    @ResponseStatus(HttpStatus.CREATED)
    public Admin createAdmin(@RequestBody @Valid AdminDTO adminDTO){
        return iAdminService.createAdmin(adminDTO);
    }
}
