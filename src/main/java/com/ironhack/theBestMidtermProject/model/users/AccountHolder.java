package com.ironhack.theBestMidtermProject.model.users;

import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.datatype.jsr310.deser.*;
import com.fasterxml.jackson.datatype.jsr310.ser.*;
import com.ironhack.theBestMidtermProject.model.*;
import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;

import javax.persistence.*;
import java.time.*;
import java.util.*;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class AccountHolder extends User{

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dateOfBirth;

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
    @OneToMany(mappedBy = "primaryOwner")
    private List<Account> primaryAccounts;
    @OneToMany(mappedBy = "secondaryOwner")
    private List<Account> secondaryAccounts;

//    Empty constructor
    public AccountHolder() {
    }

//    Constructor without mailingAddress
    public AccountHolder(LocalDateTime dateOfBirth, Address primaryAddress) {
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
    }

    //    Constructor with all parameters
    public AccountHolder(Name name, int age, Set<Role> roles, LocalDateTime dateOfBirth, Address primaryAddress,
                         Address mailingAddress) {
        super(name, age, roles);
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
        this.mailingAddress = mailingAddress;
    }

    //    Adding new accounts:
    public Account addPrimaryAccount(Account account){
        primaryAccounts.add(account);
        return account;
    }

    public Account addSecondaryAccount(Account account){
        secondaryAccounts.add(account);
        return account;
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
