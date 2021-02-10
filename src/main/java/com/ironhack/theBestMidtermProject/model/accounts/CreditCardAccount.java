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
public class CreditCardAccount extends Account{

    @Embedded
    @AttributeOverrides(value ={
            @AttributeOverride(name = "amount", column = @Column(name = "monthly_maintenance_fee")),
            @AttributeOverride(name = "currency", column = @Column(name = "currency"))
    })
    private Money monthlyMaintenanceFee;

//    CreditCardAccounts have a default interest rate of 0.2, and a minimum of 0.1
    private BigDecimal interestRate = new BigDecimal("0.2");

//    and a default credit limit of 100 with a maximum limit of 100000:
    @Embedded
    @AttributeOverrides(value ={
            @AttributeOverride(name = "amount", column = @Column(name = "credit_limit"))
    })
    private Money creditLimit = new Money(new BigDecimal("100"));
    private LocalDate lastInterestRateApplied;

//    Empty constructor
    public CreditCardAccount() {
    }

//    Constructor with all parameters
    public CreditCardAccount(Money balance, Status status, AccountHolder primaryOwner, AccountHolder secondaryOwner,
                             Money monthlyMaintenanceFee, BigDecimal interestRate, Money creditLimit, LocalDate lastInterestRateApplied) {
        super(balance, status, primaryOwner, secondaryOwner);
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
        this.interestRate = interestRate;
        this.creditLimit = creditLimit;
        this.lastInterestRateApplied = lastInterestRateApplied;
    }


//    Getters and Setters

    public Money getMonthlyMaintenanceFee() {
        return monthlyMaintenanceFee;
    }

    public void setMonthlyMaintenanceFee(Money monthlyMaintenanceFee) {
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public Money getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(Money creditLimit) {
        this.creditLimit = creditLimit;
    }

    public LocalDate getLastInterestRateApplied() {
        return lastInterestRateApplied;
    }

    public void setLastInterestRateApplied(LocalDate lastInterestRateApplied) {
        this.lastInterestRateApplied = lastInterestRateApplied;
    }
}
