package com.ironhack.theBestMidtermProject.model.users;

import com.ironhack.theBestMidtermProject.utils.classes.*;

import javax.persistence.*;

@Entity
public class Admin extends User{

//    Empty constructor
    public Admin() {
    }

//    Constructor with all parameters

    public Admin(long id, Name name, int age) {
        super(id, name, age);
    }
}
