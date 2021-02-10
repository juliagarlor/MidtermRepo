package com.ironhack.theBestMidtermProject.model.accounts;

import com.fasterxml.jackson.annotation.*;
import com.ironhack.theBestMidtermProject.model.*;
import com.ironhack.theBestMidtermProject.model.users.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;

import javax.persistence.*;
import java.math.*;
import java.util.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    @Embedded
    @AttributeOverrides(value ={
            @AttributeOverride(name = "amount", column = @Column(name = "balance")),
            @AttributeOverride(name = "currency", column = @Column(name = "currency"))
    })
    protected Money balance;

    @ManyToOne
    @JoinColumn(name = "primary_owner_id")
    protected AccountHolder primaryOwner;

    @ManyToOne(optional = true)
    @JoinColumn(name = "secondary_owner_id")
    protected AccountHolder secondaryOwner;

    @OneToMany(mappedBy = "emisor")
    protected List<Transactions> sentTransactions;
    @OneToMany(mappedBy = "receptor")
    protected List<Transactions> receivedTransactions;

//    and the penalty fee is always 40
    protected final Money PENALTY_FEE = new Money(new BigDecimal("40"));

//    Empty constructor
    public Account() {
    }

//    Constructor all parameters but secondaryOwner
    public Account(Money balance, AccountHolder primaryOwner) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
    }

//    Constructor with all parameters
    public Account(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
    }

    //    Peculiar methods
    public abstract boolean checkPassword(String password);

//    Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public User getPrimaryOwner() {
        return primaryOwner;
    }

    public void setPrimaryOwner(AccountHolder primaryOwner) {
        this.primaryOwner = primaryOwner;
    }

    public AccountHolder getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(AccountHolder secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    public List<Transactions> getSentTransactions() {
        return sentTransactions;
    }

    public void setSentTransactions(List<Transactions> sentTransactions) {
        this.sentTransactions = sentTransactions;
    }

    public List<Transactions> getReceivedTransactions() {
        return receivedTransactions;
    }

    public void setReceivedTransactions(List<Transactions> receivedTransactions) {
        this.receivedTransactions = receivedTransactions;
    }

    public Money getPENALTY_FEE() {
        return PENALTY_FEE;
    }
}
