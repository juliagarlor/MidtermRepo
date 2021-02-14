package com.ironhack.theBestMidtermProject.utils.dtos;

import javax.validation.constraints.*;
import java.math.*;

public class SavingsAcDTO {

//    We do not allow red numbers from the very beginning
    @DecimalMin(value = "0", message = "A new account can not be opened with a negative balance")
    private BigDecimal balance;
    @DecimalMax(value = "1000", message = "The maximum balance for this account must be lower than 1000.")
    @DecimalMin(value = "100", message = "The minimum balance for this account must be higher than 100.")
    private BigDecimal minimumBalance;
    @NotNull(message = "This account must have a secret key")
    @Size(min = 7, message = "The secret key must have at least 7 digits.")
    private String secretKey;

//    This will be optional, so can be set to null:
    private Long secondaryOwnerId;
    @DecimalMax(value = "0.5", message = "The interest rate can not be higher than 0.5")
    @DecimalMin(value = "0", message = "The interest rate must be a positive value.")
    private BigDecimal interestRate;

//    Getters and Setters
    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(BigDecimal minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public Long getSecondaryOwnerId() {
        return secondaryOwnerId;
    }

    public void setSecondaryOwnerId(Long secondaryOwnerId) {
        this.secondaryOwnerId = secondaryOwnerId;
    }
}
