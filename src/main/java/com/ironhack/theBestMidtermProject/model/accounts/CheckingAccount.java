package com.ironhack.theBestMidtermProject.model.accounts;

import com.ironhack.theBestMidtermProject.enums.*;
import com.ironhack.theBestMidtermProject.model.users.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;

import javax.persistence.*;
import java.math.*;

@Entity
public class CheckingAccount extends Account{

    private String secretKey;
    @ManyToOne
    @JoinColumn(name = "secondary_owner_id")
    private User secondaryOwner;
    @Enumerated(EnumType.STRING)
    private Status status;
//    checkingAccounts have a minimum balance of 250:
    private final Money MINIMUM_BALANCE = new Money(new BigDecimal("250"));
//    and the penalty fee is always 40
    private final Money PENALTY_FEE = new Money(new BigDecimal("40"));
//    and a monthly maintenance fee of 12:
    private final Money MONTHLY_MAINTENANCE_FEE = new Money(new BigDecimal("12"));

//    Empty constructor
    public CheckingAccount() {
    }

//    Constructor with all parameters
    public CheckingAccount(Money balance, User primaryOwner, String secretKey, User secondaryOwner, Status status) {
        super(balance, primaryOwner);
        this.secretKey = secretKey;
        this.secondaryOwner = secondaryOwner;
        this.status = status;
    }

//    Getters and Setters:
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

    public Money getMINIMUM_BALANCE() {
        return MINIMUM_BALANCE;
    }

    public Money getPENALTY_FEE() {
        return PENALTY_FEE;
    }

    public Money getMONTHLY_MAINTENANCE_FEE() {
        return MONTHLY_MAINTENANCE_FEE;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
