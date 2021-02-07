package com.ironhack.theBestMidtermProject.model.accounts;

import com.ironhack.theBestMidtermProject.model.users.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.enums.*;

import javax.persistence.*;
import java.math.*;

@Entity
public class StudentCheckingAccount extends Account{

    private String secretKey;
    @ManyToOne
    @JoinColumn(name = "secondary_owner_id")
    private User secondaryOwner;
    @Enumerated(EnumType.STRING)
    private Status status;
//    The penalty fee is always 40
    private final Money PENALTY_FEE = new Money(new BigDecimal("40"));

//    Empty constructor of StudentCheckingAccount
    public StudentCheckingAccount() {
    }

//    Constructor with all the parameters
    public StudentCheckingAccount(Money balance, User primaryOwner, String secretKey, User secondaryOwner,
                                  Status status) {
        super(balance, primaryOwner);
        this.secretKey = secretKey;
        this.secondaryOwner = secondaryOwner;
        this.status = status;
    }

    @Override
    public boolean checkPassword(String password) {
        return secretKey.equals(password);
    }

    //    Getters and Setters

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public User getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(User secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Money getPENALTY_FEE() {
        return PENALTY_FEE;
    }
}
