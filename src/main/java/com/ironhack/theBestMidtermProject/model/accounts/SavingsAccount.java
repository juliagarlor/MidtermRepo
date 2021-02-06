package com.ironhack.theBestMidtermProject.model.accounts;

import com.ironhack.theBestMidtermProject.enums.*;
import com.ironhack.theBestMidtermProject.model.users.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.*;

@Entity
public class SavingsAccount extends Account{

    private String secretKey;
    @ManyToOne
    @JoinColumn(name = "secondary_owner_id")
    private User secondaryOwner;
    @Enumerated(EnumType.STRING)
    private Status status;

//    SavingsAccounts have a default minimum balance of 1000, and the minimum of this value is 100
    @Min(100L)
    @Embedded
    @AttributeOverrides(value ={
            @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance")),
            @AttributeOverride(name = "currency", column = @Column(name = "currency"))
    })
    private Money minimumBalance = new Money(new BigDecimal("1000"));

//    and a default interestRate of 0.0025, and a maximum of 0.5
    @DecimalMax("0.5")
    private BigDecimal interestRate = new BigDecimal("0.0025");

//    The penalty fee is always 40
    private final Money PENALTY_FEE = new Money(new BigDecimal("40"));

//    Empty constructor
    public SavingsAccount() {
    }

//    constructor with all parameters but minimumBalance
    public SavingsAccount(Money balance, User primaryOwner, String secretKey, User secondaryOwner, Status status,
                          @DecimalMax("0.5") BigDecimal interestRate) {
        super(balance, primaryOwner);
        this.secretKey = secretKey;
        this.secondaryOwner = secondaryOwner;
        this.status = status;
        this.interestRate = interestRate;
    }

//    constructor with all parameters but interestRate
    public SavingsAccount(Money balance, User primaryOwner, String secretKey, User secondaryOwner,
                           Status status, @Min(100L) Money minimumBalance) {
        super(balance, primaryOwner);
        this.secretKey = secretKey;
        this.secondaryOwner = secondaryOwner;
        this.status = status;
        this.minimumBalance = minimumBalance;
    }

//    constructor with all parameters but both minimumBalance and interestBalance
    public SavingsAccount(Money balance, User primaryOwner, String secretKey, User secondaryOwner, Status status) {
        super(balance, primaryOwner);
        this.secretKey = secretKey;
        this.secondaryOwner = secondaryOwner;
        this.status = status;
    }

//    constructor with all parameters
    public SavingsAccount(Money balance, User primaryOwner, String secretKey, User secondaryOwner, Status status,
                          @Min(100L) Money minimumBalance, @DecimalMax("0.5") BigDecimal interestRate) {
        super(balance, primaryOwner);
        this.secretKey = secretKey;
        this.secondaryOwner = secondaryOwner;
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

    public User getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(User secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    public Money getPenaltyFee() {
        return PENALTY_FEE;
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
