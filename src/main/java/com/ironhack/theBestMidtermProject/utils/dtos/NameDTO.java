package com.ironhack.theBestMidtermProject.utils.dtos;

import com.ironhack.theBestMidtermProject.utils.enums.*;

import javax.persistence.*;
import javax.validation.constraints.*;

public class NameDTO {

//    A person should have a last name, unless you are a rock star :) ... but only a ROCK star
    @NotNull(message = "Look in your ID for your last name")
    @NotEmpty(message = "Please, introduce the last name of the owner.")
    private String lastName;

//    And a name
    @NotNull(message = "Look in your ID for your name")
    @NotEmpty(message = "Please, introduce the first name of the owner.")
    private String firstName;

//    They may have a middle name, but only if your parents hate you
    private String middleName;

//    And a salutation
    @NotNull(message = "Please, add the correct salutation")
    @Enumerated(EnumType.STRING)
    private Salutation salutation;

//    Setters and Getters
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Salutation getSalutation() {
        return salutation;
    }

    public void setSalutation(Salutation salutation) {
        this.salutation = salutation;
    }
}
