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

    private Ensambler ensambler = new Ensambler();

    public ThirdParty createThirdParty(ThirdPartyDTO thirdPartyDTO){
        Name name = ensambler.ensambleName(thirdPartyDTO.getNameDTO());
        int age = thirdPartyDTO.getAge();
        String password = thirdPartyDTO.getPassword();
        Set<Role> roles = new HashSet<>();
        String hashedKey = thirdPartyDTO.getHashedKey();

        ThirdParty newThirdParty = new ThirdParty(name, password, age, roles, hashedKey);

        roles.add(new Role("USER", newThirdParty));
        newThirdParty.setRoles(roles);
        return thirdPartyRepository.save(newThirdParty);
    }
}
