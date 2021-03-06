package com.ironhack.theBestMidtermProject.service.impl;

import com.ironhack.theBestMidtermProject.model.users.*;
import com.ironhack.theBestMidtermProject.repository.users.*;
import com.ironhack.theBestMidtermProject.service.interfaces.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class ThirdPartyService implements IThirdPartyService {

    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    private Transformer transformer = new Transformer();

    public ThirdParty createThirdParty(ThirdPartyDTO thirdPartyDTO){

//        Simple: take from the DTO and set into the new third party
        Name name = transformer.assembleName(thirdPartyDTO.getNameDTO());
        int age = thirdPartyDTO.getAge();
        String password = thirdPartyDTO.getPassword();
        Set<Role> roles = new HashSet<>();
        String hashedKey = thirdPartyDTO.getHashedKey();

        ThirdParty newThirdParty = new ThirdParty(name, password, age, roles, hashedKey);

        roles.add(new Role("THIRD_PARTY", newThirdParty));
        newThirdParty.setRoles(roles);
        return thirdPartyRepository.save(newThirdParty);
    }
}
