package com.ironhack.theBestMidtermProject.service.impl;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.model.users.*;
import com.ironhack.theBestMidtermProject.repository.users.*;
import com.ironhack.theBestMidtermProject.service.interfaces.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.*;

import java.util.*;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    public User getUser(long id) {
        Optional<User> user = userRepository.findById(id);

        if (user.isPresent()){
            return user.get();
        }else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This id does not belong to any of our clients. " +
                    "Please, intruduce a valid id.");
        }
    }
}
