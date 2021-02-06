package com.ironhack.theBestMidtermProject.model.users;

import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.sun.istack.*;

import javax.persistence.*;

@Entity
public class ThirdParty extends User{
    @NotNull
    private String hashedKey;

//    Empty constructor
    public ThirdParty() {
    }

//    Constructor with parameters
    public ThirdParty(long id, Name name, int age, String hashedKey) {
        super(id, name, age);
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
