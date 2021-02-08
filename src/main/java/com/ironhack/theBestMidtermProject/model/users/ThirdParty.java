package com.ironhack.theBestMidtermProject.model.users;

import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.sun.istack.*;

import javax.persistence.*;
import java.util.*;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class ThirdParty extends User{
    @NotNull
    private String hashedKey;

//    Empty constructor
    public ThirdParty() {
    }

//    Constructor with parameters
    public ThirdParty(Name name, int age, Set<Role> roles, String hashedKey) {
        super(name, age, roles);
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
