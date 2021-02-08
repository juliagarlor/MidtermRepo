package com.ironhack.theBestMidtermProject.controller.impl;

import com.ironhack.theBestMidtermProject.controller.interfaces.*;
import com.ironhack.theBestMidtermProject.service.interfaces.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;
import com.ironhack.theBestMidtermProject.utils.enums.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.*;
import java.util.*;

@RestController
public class AccountHolderController implements IAccountHolderController {
//
//    @Autowired
//    private IAccountHolderService iAccountHolderService;
//
//    @GetMapping("/balance/{accountId}")
//    @ResponseStatus(HttpStatus.OK)
//    public Money checkBalance(@PathVariable long accountId){
//        return iAccountHolderService.checkBalance(accountId);
//    }
//
//    @PatchMapping("/transference/{nameDTO}/{targetId}/{emisorId}")
//    @ResponseStatus(HttpStatus.OK)
//    public Money transferAmount(@PathVariable @Valid NameDTO nameDTO, @PathVariable long targetId, @PathVariable long emisorId,
//                                @RequestBody Money amount){
//        String firstName = nameDTO.getFirstName();
//        String lastName = nameDTO.getLastName();
//        String middleName = nameDTO.getMiddleName();
//        Salutation salutation = nameDTO.getSalutation();
//        Name name = new Name(firstName, lastName, middleName, salutation);
//        return iAccountHolderService.transferAmount(name, targetId, emisorId, amount);
//    }
}
