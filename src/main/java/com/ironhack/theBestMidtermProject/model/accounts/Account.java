package com.ironhack.theBestMidtermProject.model.accounts;

import com.ironhack.theBestMidtermProject.model.*;
import com.ironhack.theBestMidtermProject.model.users.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "emisorId")
    protected List<Transactions> sentTransactions;
    @OneToMany(mappedBy = "receptorId")
    protected List<Transactions> receivedTransactions;

//    Empty constructor
    public Account() {
    }

//    Constructor all parameters but secondaryOwner
    public Account(Long id, Money balance, AccountHolder primaryOwner) {
        this.id = id;
        this.balance = balance;
        this.primaryOwner = primaryOwner;
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
}
