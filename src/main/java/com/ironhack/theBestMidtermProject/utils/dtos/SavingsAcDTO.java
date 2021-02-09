package com.ironhack.theBestMidtermProject.utils.dtos;

import com.ironhack.theBestMidtermProject.model.users.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import javax.validation.constraints.*;
import java.math.*;
import java.util.*;

public class SavingsAcDTO {
    @DecimalMin(value = "0", message = "A new account can not be opened with a negative balance")
    private BigDecimal balance;
    @DecimalMax(value = "1000", message = "The maximum balance for this account must be lower than 1000.")
    @DecimalMin(value = "100", message = "The minimum balance for this account must be higher than 100.")
    private BigDecimal minimumBalance;
    @NotNull(message = "This account must have a secret key")
    @Size(min = 7, message = "The secret key must have at least 7 digits.")
    private String secretKey;
    private Optional<AccountHolder> secondaryOwner;
    @DecimalMax(value = "0.5", message = "The interest rate can not be higher than 0.5")
    @DecimalMin(value = "0", message = "The interest rate must be a positive value.")
    private BigDecimal interestRate;

//    Constructor without minimumBalance and interestRate
    public SavingsAcDTO(@DecimalMin(value = "0", message = "A new account can not be opened with a negative balance") BigDecimal balance,
                        @NotNull(message = "This account must have a secret key")
                        @Size(min = 7, message = "The secret key must have at least 7 digits.")String secretKey,
                        Optional<AccountHolder> secondaryOwner) {
        this.balance = balance;
        this.minimumBalance = new BigDecimal("1000");
        this.secretKey = secretKey;
        this.secondaryOwner = secondaryOwner;
        this.interestRate = new BigDecimal("0.0025");
    }

//    Constructor without minimumBalance
    public SavingsAcDTO(@DecimalMin(value = "0", message = "A new account can not be opened with a negative balance") BigDecimal balance,
                        @NotNull(message = "This account must have a secret key")
                        @Size(min = 7, message = "The secret key must have at least 7 digits.")String secretKey, Optional<AccountHolder> secondaryOwner,
                        @DecimalMax(value = "0.5", message = "The interest rate can not be higher than 0.5")
                        @DecimalMin(value = "0", message = "The interest rate must be a positive value.")BigDecimal interestRate) {
        this.balance = balance;
        this.minimumBalance = new BigDecimal("1000");
        this.secretKey = secretKey;
        this.secondaryOwner = secondaryOwner;
        this.interestRate = interestRate;
    }

//    Constructor without interestRate
    public SavingsAcDTO(@DecimalMin(value = "0", message = "A new account can not be opened with a negative balance") BigDecimal balance,
                        @DecimalMax(value = "1000", message = "The maximum balance for this account must be lower than 1000.")
                        @DecimalMin(value = "100", message = "The minimum balance for this account must be higher than 100.") BigDecimal minimumBalance,
                        @NotNull(message = "This account must have a secret key")
                        @Size(min = 7, message = "The secret key must have at least 7 digits.")String secretKey, Optional<AccountHolder> secondaryOwner) {
        this.balance = balance;
        this.minimumBalance = minimumBalance;
        this.secretKey = secretKey;
        this.secondaryOwner = secondaryOwner;
        this.interestRate = new BigDecimal("0.0025");
    }

//    Constructor with all parameters
    public SavingsAcDTO(@DecimalMin(value = "0", message = "A new account can not be opened with a negative balance") BigDecimal balance,
                        @DecimalMax(value = "1000", message = "The maximum balance for this account must be lower than 1000.")
                        @DecimalMin(value = "100", message = "The minimum balance for this account must be higher than 100.") BigDecimal minimumBalance,
                        @NotNull(message = "This account must have a secret key")
                        @Size(min = 7, message = "The secret key must have at least 7 digits.")String secretKey, Optional<AccountHolder> secondaryOwner,
                        @DecimalMax(value = "0.5", message = "The interest rate can not be higher than 0.5")
                        @DecimalMin(value = "0", message = "The interest rate must be a positive value.")BigDecimal interestRate) {
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

    public Optional<AccountHolder> getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(Optional<AccountHolder> secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }
}
