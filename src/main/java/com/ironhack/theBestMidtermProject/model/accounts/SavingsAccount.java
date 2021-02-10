package com.ironhack.theBestMidtermProject.model.accounts;

import com.ironhack.theBestMidtermProject.model.users.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.enums.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.*;
import java.time.*;
import java.util.*;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class SavingsAccount extends Account{

    private String secretKey;

//    SavingsAccounts have a default minimum balance of 1000, and the minimum of this value is 100
    @Embedded
    @AttributeOverrides(value ={
            @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance")),
            @AttributeOverride(name = "currency", column = @Column(name = "currency"))
    })
    private Money minimumBalance = new Money(new BigDecimal("1000"));

//    and a default interestRate of 0.0025, and a maximum of 0.5
    private BigDecimal interestRate = new BigDecimal("0.0025");
    private LocalDate lastInterestRateApplied;

//    Empty constructor
    public SavingsAccount() {
    }

//    Constructor with all parameters
    public SavingsAccount(Money balance, Status status, AccountHolder primaryOwner, AccountHolder secondaryOwner,
                          String secretKey, Money minimumBalance, BigDecimal interestRate, LocalDate lastInterestRateApplied) {
        super(balance, status, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
        this.lastInterestRateApplied = lastInterestRateApplied;
    }

    //    Getters and Setters
    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Money getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(Money minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public LocalDate getLastInterestRateApplied() {
        return lastInterestRateApplied;
    }

    public void setLastInterestRateApplied(LocalDate lastInterestRateApplied) {
        this.lastInterestRateApplied = lastInterestRateApplied;
    }
}
