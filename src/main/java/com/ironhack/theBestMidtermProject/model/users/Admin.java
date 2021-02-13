package com.ironhack.theBestMidtermProject.model.users;

import com.ironhack.theBestMidtermProject.utils.classes.*;

import javax.persistence.*;
import java.util.*;

//An admin is pretty much a pure user, it will manage the creation of accounts but do not have any of its own

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Admin extends User{

//    Empty constructor
    public Admin() {
    }

//    Constructor with all parameters
    public Admin(Name name, String password, int age, Set<Role> roles) {
        super(name, password, age, roles);
    }
}
