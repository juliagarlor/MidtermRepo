package com.ironhack.theBestMidtermProject.model.accounts;

import com.ironhack.theBestMidtermProject.model.users.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.enums.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.*;
import java.util.*;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class SavingsAccount extends Account{

    private String secretKey;
    @Enumerated(EnumType.STRING)
    private Status status;

//    SavingsAccounts have a default minimum balance of 1000, and the minimum of this value is 100
    @Embedded
    @AttributeOverrides(value ={
            @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance")),
            @AttributeOverride(name = "currency", column = @Column(name = "currency"))
    })
    private Money minimumBalance = new Money(new BigDecimal("1000"));

//    and a default interestRate of 0.0025, and a maximum of 0.5
    private BigDecimal interestRate = new BigDecimal("0.0025");

//    Empty constructor
    public SavingsAccount() {
    }

//    constructor with all parameters but minimumBalance
    public SavingsAccount(Long id, Money balance, AccountHolder primaryOwner, String secretKey,
                          Status status, BigDecimal interestRate) {
        super(id, balance, primaryOwner);
        this.secretKey = secretKey;
        this.status = status;
        this.interestRate = interestRate;
    }

//    constructor with all parameters but interestRate
    public SavingsAccount(Long id, Money balance, AccountHolder primaryOwner, String secretKey,
                          Status status, Money minimumBalance) {
        super(id, balance, primaryOwner);
        this.secretKey = secretKey;
        this.status = status;
        this.minimumBalance = minimumBalance;
    }

//    constructor with all parameters but both minimumBalance and interestBalance
    public SavingsAccount(Long id, Money balance, AccountHolder primaryOwner, String secretKey,
                          Status status) {
        super(id, balance, primaryOwner);
        this.secretKey = secretKey;
        this.status = status;
    }

//    constructor with all parameters
    public SavingsAccount(Long id, Money balance, AccountHolder primaryOwner, String secretKey,
                          Status status, Money minimumBalance, BigDecimal interestRate) {
        super(id, balance, primaryOwner);
        this.secretKey = secretKey;
        this.status = status;
        this.minimumBalance = minimumBalance;
        this.interestRate = interestRate;
    }

    @Override
    public boolean checkPassword(String password) {
        return secretKey.equals(password);
    }

    //    Getters and Setters
    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
}
