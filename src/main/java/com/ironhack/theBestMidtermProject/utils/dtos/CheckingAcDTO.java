package com.ironhack.theBestMidtermProject.utils.dtos;

import javax.validation.constraints.*;
import javax.validation.constraints.NotNull;
import java.math.*;

public class CheckingAcDTO {

    @DecimalMin(value = "250", message = "The opening account balance should be above 250")
    private BigDecimal balance;

//    We want our key to be at least 7 characters long so it is safer
    @NotNull(message = "The account must have a secret key")
    @Size(min = 7, message = "The secret key must have at least 7 digits.")
    private String secretKey;

//    Secondary owner is optional, so it may be null
    private long secondaryOwner;

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

    public long getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(long secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }
}
