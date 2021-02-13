package com.ironhack.theBestMidtermProject.model.accounts;

import com.ironhack.theBestMidtermProject.model.users.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.enums.*;

import javax.persistence.*;
import java.math.*;
import java.time.*;

//Credit card accounts have a monthly maintenance fee, an interest rate than will be applied monthly, a credit limit and
// the date of the last application of the interest and the maintenance fee.

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class CreditCardAccount extends Account{

    @Embedded
    @AttributeOverrides(value ={
            @AttributeOverride(name = "amount", column = @Column(name = "monthly_maintenance_fee")),
            @AttributeOverride(name = "currency", column = @Column(name = "currency"))
    })
    private Money monthlyMaintenanceFee;

//    The default interest rate is 0.2, and will be managed in the service when creating a new account, as well as the
//    credit limit
    private BigDecimal interestRate;

//    Credit limit has a default value of 100
    @Embedded
    @AttributeOverrides(value ={
            @AttributeOverride(name = "amount", column = @Column(name = "credit_limit"))
    })
    private Money creditLimit;
    private LocalDate lastInterestRateApplied;

//    Empty constructor
    public CreditCardAccount() {
    }

//    Constructor with all parameters
    public CreditCardAccount(Money balance, Status status, AccountHolder primaryOwner, AccountHolder secondaryOwner,
                             Money monthlyMaintenanceFee, BigDecimal interestRate, Money creditLimit,
                             LocalDate lastInterestRateApplied) {
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
