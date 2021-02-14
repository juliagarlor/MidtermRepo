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
public class ThirdPartyController implements IThirdPartyController {

    @Autowired
    private IThirdPartyService iThirdPartyService;

    /*createThirdParty takes the info from a third party to create and return a ThirdPary object. This route can be only
    used by admins*/

    @PostMapping("/register/third-party")
    @ResponseStatus(HttpStatus.CREATED)
    public ThirdParty createThirdParty(@RequestBody @Valid ThirdPartyDTO thirdPartyDTO){
        return iThirdPartyService.createThirdParty(thirdPartyDTO);
    }
}
