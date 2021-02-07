package com.ironhack.theBestMidtermProject.model.users;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.sun.istack.*;

import javax.persistence.*;
import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Embedded
    private Name name;
    private int age;

//    We will add a boolean in order to know whether the user is logged or not, allowing to perform operations
    private boolean loggedIn;

    @OneToMany(mappedBy = "primaryOwner")
    private Map<Long, Account> principalAccounts;

    @OneToMany(mappedBy = "secondaryOwner")
    private Map<Long, Account> secondaryAccounts;

//    Empty constructor
    public User() {
    }

//    Constructor with all parameters

    public User(long id, Name name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
//        When creating an object, the user has not log into the account yet
        this.loggedIn = false;
    }

//    Adding new accounts:
    public Account addPrincipalAccount(Account account){
        principalAccounts.put(account.getId(), account);
        return account;
    }

    public Account addSecondaryAccount(Account account){
        principalAccounts.put(account.getId(), account);
        return account;
    }

//    Getter y Setters

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    public Map<Long, Account> getPrincipalAccounts() {
        return principalAccounts;
    }

    public void setPrincipalAccounts(Map<Long, Account> principalAccounts) {
        this.principalAccounts = principalAccounts;
    }

    public Map<Long, Account> getSecondaryAccounts() {
        return secondaryAccounts;
    }

    public void setSecondaryAccounts(Map<Long, Account> secondaryAccounts) {
        this.secondaryAccounts = secondaryAccounts;
    }
}
