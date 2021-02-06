package com.ironhack.theBestMidtermProject.model.users;

import javax.persistence.*;

@Entity
public class Admin extends User{

//    Empty constructor
    public Admin() {
    }

//    Constructor with all parameters

    public Admin(long id, String name) {
        super(id, name);
    }
}
