package com.ironhack.theBestMidtermProject.model.accounts;

import com.ironhack.theBestMidtermProject.model.users.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.enums.*;

import javax.persistence.*;
import java.math.*;
import java.util.*;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class CheckingAccount extends Account{

    private String secretKey;
    @Transient
//    checkingAccounts have a minimum balance of 250:
    private final Money MINIMUM_BALANCE = new Money(new BigDecimal("250"));
    @Transient
//    and a monthly maintenance fee of 12:
    private final Money MONTHLY_MAINTENANCE_FEE = new Money(new BigDecimal("12"));

//    Empty constructor
    public CheckingAccount() {
    }

//    Constructor with all parameters
    public CheckingAccount(Money balance, Status status, AccountHolder primaryOwner, AccountHolder secondaryOwner, String secretKey) {
        super(balance, status, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
    }

    //    Getters and Setters:
    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Money getMINIMUM_BALANCE() {
        return MINIMUM_BALANCE;
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
