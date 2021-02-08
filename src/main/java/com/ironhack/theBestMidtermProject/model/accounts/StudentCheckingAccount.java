package com.ironhack.theBestMidtermProject.model.accounts;

import com.ironhack.theBestMidtermProject.model.users.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.enums.*;

import javax.persistence.*;
import java.math.*;
import java.util.*;

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class StudentCheckingAccount extends Account{

    private String secretKey;
    @Enumerated(EnumType.STRING)
    private Status status;

//    Empty constructor of StudentCheckingAccount
    public StudentCheckingAccount() {
    }

//    Constructor with all the parameters
    public StudentCheckingAccount(Long id, Money balance, AccountHolder primaryOwner,
                                  String secretKey, Status status) {
        super(id, balance, primaryOwner);
        this.secretKey = secretKey;
        this.status = status;
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
}
