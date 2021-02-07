package com.ironhack.theBestMidtermProject.utils.dtos;

import com.ironhack.theBestMidtermProject.model.users.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import javax.validation.constraints.*;
import java.math.*;
import java.util.*;

public class SavingsAcDTO {
    @DecimalMin(value = "0", message = "A new account can not be opened with a negative balance")
    private BigDecimal balance;
    @DecimalMin(value = "100", message = "The minimum balance for this account must be higher than 100.")
    private BigDecimal minimumBalance;
    @NotNull(message = "This account must have a secret key")
    private String secretKey;
    private Optional<User> secondaryOwner;
    @DecimalMax(value = "0.5", message = "The interest rate can not be higher than 0.5")
    private BigDecimal interestRate;
    private final Money PENALTY_FEE = new Money(new BigDecimal("40"));

//    Constructor without minimumBalance and interestRate
    public SavingsAcDTO(@DecimalMin(value = "0", message = "A new account can not be opened with a negative balance") BigDecimal balance,
                        @NotNull(message = "This account must have a secret key") String secretKey, Optional<User> secondaryOwner) {
        this.balance = balance;
        this.minimumBalance = new BigDecimal("1000");
        this.secretKey = secretKey;
        this.secondaryOwner = secondaryOwner;
        this.interestRate = new BigDecimal("0.0025");
    }

//    Constructor without minimumBalance
    public SavingsAcDTO(@DecimalMin(value = "0", message = "A new account can not be opened with a negative balance") BigDecimal balance,
                        @NotNull(message = "This account must have a secret key") String secretKey, Optional<User> secondaryOwner,
                        @DecimalMax(value = "0.5", message = "The interest rate can not be higher than 0.5") BigDecimal interestRate) {
        this.balance = balance;
        this.minimumBalance = new BigDecimal("1000");
        this.secretKey = secretKey;
        this.secondaryOwner = secondaryOwner;
        this.interestRate = interestRate;
    }

//    Constructor without interestRate
    public SavingsAcDTO(@DecimalMin(value = "0", message = "A new account can not be opened with a negative balance") BigDecimal balance,
                        @DecimalMin(value = "100", message = "The minimum balance for this account must be higher than 100.") BigDecimal minimumBalance,
                        @NotNull(message = "This account must have a secret key") String secretKey, Optional<User> secondaryOwner) {
        this.balance = balance;
        this.minimumBalance = minimumBalance;
        this.secretKey = secretKey;
        this.secondaryOwner = secondaryOwner;
        this.interestRate = new BigDecimal("0.0025");
    }

//    Constructor with all parameters
    public SavingsAcDTO(@DecimalMin(value = "0", message = "A new account can not be opened with a negative balance") BigDecimal balance,
                        @DecimalMin(value = "100", message = "The minimum balance for this account must be higher than 100.") BigDecimal minimumBalance,
                        @NotNull(message = "This account must have a secret key") String secretKey, Optional<User> secondaryOwner,
                        @DecimalMax(value = "0.5", message = "The interest rate can not be higher than 0.5") BigDecimal interestRate) {
        this.balance = balance;
        this.minimumBalance = minimumBalance;
        this.secretKey = secretKey;
        this.secondaryOwner = secondaryOwner;
        this.interestRate = interestRate;
    }

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

    public Optional<User> getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(Optional<User> secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public Money getPENALTY_FEE() {
        return PENALTY_FEE;
    }
}
