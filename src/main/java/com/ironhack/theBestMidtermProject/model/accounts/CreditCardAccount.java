package com.ironhack.theBestMidtermProject.model.accounts;

import com.ironhack.theBestMidtermProject.model.users.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.*;

@Entity
public class CreditCardAccount extends Account{

//    The secondary owner is optional in a creditCardAccount
    @ManyToOne
    @JoinColumn(name = "secondary_owner_id")
    private User secondaryOwner;
    @Embedded
    @AttributeOverrides(value ={
            @AttributeOverride(name = "amount", column = @Column(name = "monthly_maintenance_fee")),
            @AttributeOverride(name = "currency", column = @Column(name = "currency"))
    })
    private Money monthlyMaintenanceFee;

//    CreditCardAccounts have a default interest rate of 0.2, and a minimum of 0.1
    @DecimalMin("0.1")
    private BigDecimal interestRate = new BigDecimal("0.2");

//    todo pregunta si Max vale para un objeto Money
//    and a default credit limit of 100 with a maximum limit of 100000:
    @Max(100000L)
    @Embedded
    @AttributeOverrides(value ={
            @AttributeOverride(name = "amount", column = @Column(name = "credit_limit"))
    })
    private Money creditLimit = new Money(new BigDecimal("100"));

//    Empty constructor
    public CreditCardAccount() {
    }

//    Constructor without interestRate and creditLimit
    public CreditCardAccount(Money balance, User primaryOwner, User secondaryOwner, Money monthlyMaintenanceFee) {
        super(balance, primaryOwner);
        this.secondaryOwner = secondaryOwner;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
    }

//    Constructor without interestRate
    public CreditCardAccount(Money balance, User primaryOwner, User secondaryOwner, Money monthlyMaintenanceFee,
                             @Max(100000L) Money creditLimit) {
        super(balance, primaryOwner);
        this.secondaryOwner = secondaryOwner;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
        this.creditLimit = creditLimit;
    }

//    Constructor without creditLimit
    public CreditCardAccount(Money balance, User primaryOwner, User secondaryOwner, Money monthlyMaintenanceFee,
                             @DecimalMin("0.1") BigDecimal interestRate) {
        super(balance, primaryOwner);
        this.secondaryOwner = secondaryOwner;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
        this.interestRate = interestRate;
    }

//    Constructor in which we introduce all parameters
    public CreditCardAccount(Money balance, User primaryOwner, User secondaryOwner, Money monthlyMaintenanceFee,
                             @DecimalMin("0.1") BigDecimal interestRate, @Max(100000L) Money creditLimit) {
        super(balance, primaryOwner);
        this.secondaryOwner = secondaryOwner;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
        this.interestRate = interestRate;
        this.creditLimit = creditLimit;
    }

    @Override
    public boolean checkPassword(String password) {
//        Since a credit card account does not have a password:
        return false;
    }

    //    Getters and Setters

    public User getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(User secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

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
}
