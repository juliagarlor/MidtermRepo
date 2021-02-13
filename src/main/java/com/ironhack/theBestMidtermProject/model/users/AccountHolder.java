package com.ironhack.theBestMidtermProject.model.users;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.datatype.jsr310.deser.*;
import com.fasterxml.jackson.datatype.jsr310.ser.*;
import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import java.time.*;
import java.util.*;

//An account holder has a date of birth, a primary address and an optional mailingAddress if needed, a list of primary
// accounts (this user is the primary owner) and another list of secondary accounts (in which it is the secondary owner)

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class AccountHolder extends User{

//    Deserializing and serializing LocalDates so we have no problem with JSON format
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dateOfBirth;

//    Address is an embedded object whose class can be found in utils -> classes
    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "number", column = @Column(name = "primary_address_number", columnDefinition = "varchar(60)")),
            @AttributeOverride(name = "street", column = @Column(name = "primary_address_street", columnDefinition = "varchar(60)")),
            @AttributeOverride(name = "city", column = @Column(name = "primary_address_city", columnDefinition = "varchar(60)")),
            @AttributeOverride(name = "country", column = @Column(name = "primary_address_country", columnDefinition = "varchar(60)")),

    })
    private Address primaryAddress;
    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "number", column = @Column(name = "mailing_address_number")),
            @AttributeOverride(name = "street", column = @Column(name = "mailing_address_street", columnDefinition = "varchar(60)")),
            @AttributeOverride(name = "city", column = @Column(name = "mailing_address_city", columnDefinition = "varchar(60)")),
            @AttributeOverride(name = "country", column = @Column(name = "mailing_address_country", columnDefinition = "varchar(60)")),

    })
    private Address mailingAddress;

//    Objects of these two lists will be searched in EAGER mode
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "primaryOwner")
    @JsonIgnore
    private List<Account> primaryAccounts;
    @LazyCollection(LazyCollectionOption.FALSE)
    @OneToMany(mappedBy = "secondaryOwner")
    @JsonIgnore
    private List<Account> secondaryAccounts;

//    Empty constructor
    public AccountHolder() {
    }

//    Constructor with all parameters
    public AccountHolder(Name name, String password, int age, Set<Role> roles, LocalDateTime dateOfBirth,
                         Address primaryAddress, Address mailingAddress) {
        super(name, password, age, roles);
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
    }

//    Getters and Setters
    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Address getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(Address primaryAddress) {
        this.primaryAddress = primaryAddress;
    }

    public Address getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(Address mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public List<Account> getPrimaryAccounts() {
        return primaryAccounts;
    }

    public void setPrimaryAccounts(List<Account> primaryAccounts) {
        this.primaryAccounts = primaryAccounts;
    }

    public List<Account> getSecondaryAccounts() {
        return secondaryAccounts;
    }

    public void setSecondaryAccounts(List<Account> secondaryAccounts) {
        this.secondaryAccounts = secondaryAccounts;
    }
}
