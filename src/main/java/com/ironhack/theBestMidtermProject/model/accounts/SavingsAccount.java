package com.ironhack.theBestMidtermProject.model.accounts;

import com.ironhack.theBestMidtermProject.model.users.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.enums.*;

import javax.persistence.*;
import java.math.*;
import java.time.*;

//Savings accounts have a secret key, a minimum balance, an interest rate, and the date of the last application of the
// interest rate

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class SavingsAccount extends Account{

    private String secretKey;

//    The default minimum balance is 1000, and will be managed on the service
    @Embedded
    @AttributeOverrides(value ={
            @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance")),
            @AttributeOverride(name = "currency", column = @Column(name = "currency"))
    })
    private Money minimumBalance;

//    and the default interestRate is 0.0025
    private BigDecimal interestRate;
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
