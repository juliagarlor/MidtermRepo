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
public class AdminService implements IAdminService {

    @Autowired
    private AdminRepository adminRepository;

    private Transformer transformer = new Transformer();

    public Admin createAdmin(AdminDTO adminDTO){
        Name name = transformer.assembleName(adminDTO.getNameDTO());
        int age = adminDTO.getAge();
        String password = adminDTO.getPassword();
        Set<Role> roles = new HashSet<>();

        Admin newAdmin = new Admin(name,password, age, roles);

        roles.add(new Role("ADMIN", newAdmin));
        newAdmin.setRoles(roles);
        return adminRepository.save(newAdmin);
    }

}
