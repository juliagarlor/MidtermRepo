package com.ironhack.theBestMidtermProject.model.users;

import com.ironhack.theBestMidtermProject.utils.classes.*;

import javax.persistence.*;
import java.util.*;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Admin extends User{

//    Empty constructor
    public Admin() {
    }

//    Constructor with all parameters
    public Admin(Name name, int age, Set<Role> roles) {
        super(name, age, roles);
    }
}
