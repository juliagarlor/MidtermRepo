package com.ironhack.theBestMidtermProject.utils.dtos;

import com.ironhack.theBestMidtermProject.model.users.*;

import javax.validation.constraints.*;
import java.math.*;
import java.util.*;

public class CreditAcDTO {

    @DecimalMax("100000")
    private BigDecimal creditLimit;
    @DecimalMin(value = "0", message = "A new account must have a positive valance")
    private BigDecimal balance;
    private Optional<AccountHolder> secondaryOwner;
    @DecimalMin(value = "0", message = "The maintenance fee must be positive")
    private BigDecimal monthlyMaintenanceFee;
    @DecimalMin(value = "0.1", message = "Interest rate should be higher than 0.1")
    private BigDecimal interestRate;

//    Constructor without creditLimit and interestRate
    public CreditAcDTO(@DecimalMin(value = "0", message = "A new account must have a positive valance") BigDecimal balance,
                       Optional<AccountHolder> secondaryOwner,
                       @DecimalMin(value = "0", message = "The maintenance fee must be positive") BigDecimal monthlyMaintenanceFee) {
        this.balance = balance;
        this.secondaryOwner = secondaryOwner;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
    }

//    Constructor without creditLimit
    public CreditAcDTO(@DecimalMin(value = "0", message = "A new account must have a positive valance") BigDecimal balance,
                       Optional<AccountHolder> secondaryOwner,
                       @DecimalMin(value = "0", message = "The maintenance fee must be positive") BigDecimal monthlyMaintenanceFee,
                       @DecimalMin(value = "0.1", message = "Interest rate should be higher than 0.1") BigDecimal interestRate) {
        this.creditLimit = new BigDecimal("100");
        this.balance = balance;
        this.secondaryOwner = secondaryOwner;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
        this.interestRate = interestRate;
    }

//    Constructor without interestRate
    public CreditAcDTO(@DecimalMax("100000") BigDecimal creditLimit, @DecimalMin(value = "0", message = "A new account must have a positive valance")
            BigDecimal balance, Optional<AccountHolder> secondaryOwner,
                       @DecimalMin(value = "0", message = "The maintenance fee must be positive") BigDecimal monthlyMaintenanceFee) {
        this.creditLimit = creditLimit;
        this.balance = balance;
        this.secondaryOwner = secondaryOwner;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
        this.interestRate = new BigDecimal("0.2");
    }

    //    Constructor with all params
    public CreditAcDTO(@DecimalMax("100000") BigDecimal creditLimit, @DecimalMin(value = "0", message = "A new account must have a positive valance")
            BigDecimal balance, Optional<AccountHolder> secondaryOwner,
                       @DecimalMin(value = "0", message = "The maintenance fee must be positive") BigDecimal monthlyMaintenanceFee,
                       @DecimalMin(value = "0.1", message = "Interest rate should be higher than 0.1") BigDecimal interestRate) {
        this.creditLimit = creditLimit;
        this.balance = balance;
        this.secondaryOwner = secondaryOwner;
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
        this.interestRate = interestRate;
    }

//    Getters and Setters

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Optional<AccountHolder> getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(Optional<AccountHolder> secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    public BigDecimal getMonthlyMaintenanceFee() {
        return monthlyMaintenanceFee;
    }

    public void setMonthlyMaintenanceFee(BigDecimal monthlyMaintenanceFee) {
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }
}
