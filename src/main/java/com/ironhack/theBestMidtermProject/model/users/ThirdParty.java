package com.ironhack.theBestMidtermProject.model.users;

import com.ironhack.theBestMidtermProject.utils.classes.*;

import javax.persistence.*;
import java.util.*;

//Third party represent a user from a different bank, so it will not have accounts in our own bank, but can act as an
// account holder in order to perform transactions. Third parties have a hashed key besides of their login password.

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class ThirdParty extends User{

    private String hashedKey;

//    Empty constructor
    public ThirdParty() {
    }

//    Constructor with parameters
    public ThirdParty(Name name, String password, int age, Set<Role> roles, String hashedKey) {
        super(name, password, age, roles);
        this.hashedKey = hashedKey;
    }


//    Getters and Setters
    public String getHashedKey() {
        return hashedKey;
    }

    public void setHashedKey(String hashedKey) {
        this.hashedKey = hashedKey;
    }
}
