package com.ironhack.theBestMidtermProject.utils.dtos;

import com.ironhack.theBestMidtermProject.utils.enums.*;

import javax.persistence.*;
import javax.validation.constraints.*;

public class NameDTO {
    @NotNull(message = "Please, introduce the last name of the owner.")
    private String lastName;
    @NotNull(message = "Please, introduce the first name of the owner.")
    private String firstName;
    private String middleName;
    @NotNull(message = "Please, add the correct salutation")
    @Enumerated(EnumType.STRING)
    private Salutation salutation;

//    Constructor without middleName

    public NameDTO(@NotNull(message = "Please, introduce the last name of the owner.") String lastName,
                   @NotNull(message = "Please, introduce the first name of the owner.") String firstName,
                   @NotNull(message = "Please, add the correct salutation") Salutation salutation) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.salutation = salutation;
    }

//    Constructor with all the parameters


    public NameDTO(@NotNull(message = "Please, introduce the last name of the owner.") String lastName,
                   @NotNull(message = "Please, introduce the first name of the owner.") String firstName, String middleName,
                   @NotNull(message = "Please, add the correct salutation") Salutation salutation) {
        this.lastName = lastName;
        this.firstName = firstName;
        this.middleName = middleName;
        this.salutation = salutation;
    }

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
