package com.ironhack.theBestMidtermProject.utils.dtos;

import com.ironhack.theBestMidtermProject.model.users.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.enums.*;
import com.sun.istack.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import javax.validation.constraints.NotNull;
import java.math.*;
import java.util.*;

public class CheckingAcDTO {

    @DecimalMin(value = "250", message = "The opening account balance should be above 250")
    private BigDecimal balance;
    @NotNull(message = "The account must have a secret key")
//    We want our key to be at least 7 characters long so it is safer
    @Size(min = 7)
    private String secretKey;
    private Optional<User> secondaryOwner;

//    Constructor with secondary owner
    public CheckingAcDTO(@DecimalMin(value = "250", message = "The opening account balance should be above 250") BigDecimal balance,
                         @NotNull(message = "The account must have a secret key") @Size(min = 7) String secretKey, Optional<User> secondaryOwner) {
        this.balance = balance;
        this.secretKey = secretKey;
        this.secondaryOwner = secondaryOwner;
    }

//    Getters and Setters

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Optional<User> getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(Optional<User> secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }
}
