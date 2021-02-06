package com.ironhack.theBestMidtermProject.model.users;

import com.ironhack.theBestMidtermProject.utils.classes.*;

import javax.persistence.*;
import java.time.*;

@Entity
public class AccountHolder extends User{
    private LocalDate dateOfBirth;

    @Embedded
    private Address primaryAddress;
    private String mailingAddress;

//    Empty constructor
    public AccountHolder() {
    }

//    Constructor with all parameters but mailingAddress
    public AccountHolder(long id, Name name, LocalDate dateOfBirth, Address primaryAddress) {
        super(id, name);
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
    }

//    Constructor with all parameters
    public AccountHolder(long id, Name name, LocalDate dateOfBirth, Address primaryAddress, String mailingAddress) {
        super(id, name);
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
    }

//    Getters and Setters
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Address getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(Address primaryAddress) {
        this.primaryAddress = primaryAddress;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }
}
