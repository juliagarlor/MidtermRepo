package com.ironhack.theBestMidtermProject.utils.dtos;

import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.databind.deser.std.*;
import com.fasterxml.jackson.databind.ser.std.*;
import com.fasterxml.jackson.datatype.jsr310.deser.*;
import com.fasterxml.jackson.datatype.jsr310.ser.*;
import com.ironhack.theBestMidtermProject.model.users.*;

import javax.validation.constraints.*;
import java.math.*;
import java.util.*;

public class CreditAcDTO {

    @DecimalMax(value = "100000", message = "The top credit limit is 100000")
    @DecimalMin(value = "100", message = "The minimum credit limit is 100")
    private BigDecimal creditLimit;
    @DecimalMin(value = "0", message = "A new account must have a positive balance")
    private BigDecimal balance;
    private Long secondaryOwnerId;
    @DecimalMin(value = "0", message = "The maintenance fee must be positive")
    private BigDecimal monthlyMaintenanceFee;
    @DecimalMax(value = "0.2", message = "Interest rate should be lower than 0.2")
    @DecimalMin(value = "0.1", message = "Interest rate should be higher than 0.1")
    private BigDecimal interestRate;

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

    public Long getSecondaryOwnerId() {
        return secondaryOwnerId;
    }

    public void setSecondaryOwnerId(Long secondaryOwnerId) {
        this.secondaryOwnerId = secondaryOwnerId;
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
