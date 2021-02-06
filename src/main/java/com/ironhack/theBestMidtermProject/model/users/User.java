package com.ironhack.theBestMidtermProject.model.users;

import com.sun.istack.*;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

//    todo ponle algún filtro a los nombres para que tengan dos partes al menos
    @NotNull
    private String name;

//    Empty constructor
    public User() {
    }

//    Constructor with all parameters

    public User(long id, String name) {
        this.id = id;
        this.name = name;
    }

//    Getter y Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
