package com.ironhack.theBestMidtermProject.utils.classes;

import com.ironhack.theBestMidtermProject.utils.enums.*;

import javax.persistence.*;

@Embeddable
public class Name {

    @Column(columnDefinition = "varchar(60)")
    private String lastName;
    @Column(columnDefinition = "varchar(60)")
    private String firstName;
    @Column(columnDefinition = "varchar(60)")
    private String middleName;
    @Column(columnDefinition = "varchar(60)")
    @Enumerated(EnumType.STRING)
    private Salutation salutation;

//    Constructor without middle name
    public Name(String lastName, String firstName, Salutation salutation) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.salutation = salutation;
    }

//    Constructor with middle firstName
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
