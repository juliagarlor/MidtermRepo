package com.ironhack.theBestMidtermProject.utils.classes;

import com.ironhack.theBestMidtermProject.utils.enums.*;

import javax.persistence.*;

/*A name has a last name, a first name, optionally a middle name (mandatory if you have royal blood), and a salutation.*/

@Embeddable
public class Name {

    private String lastName;
    private String firstName;
    private String middleName;
    @Enumerated(EnumType.STRING)
    private Salutation salutation;

    public Name() {
    }

//    Constructor with all parameters
    public Name(String lastName, String firstName, String middleName, Salutation salutation) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.salutation = salutation;
    }

//    Getters and Setters
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
