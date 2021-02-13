package com.ironhack.theBestMidtermProject.model.accounts;

import com.ironhack.theBestMidtermProject.model.users.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.enums.*;

import javax.persistence.*;
import java.math.*;
import java.util.*;

//Student checking account only has a secret key.

@Entity
@PrimaryKeyJoinColumn(name = "id")
public class StudentCheckingAccount extends Account{

    private String secretKey;

//    Empty constructor
    public StudentCheckingAccount() {
    }

//    Constructor with all the parameters
    public StudentCheckingAccount(Money balance, Status status, AccountHolder primaryOwner, AccountHolder secondaryOwner,
                                  String secretKey) {
        super(balance, status, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
    }


//    Getters and Setters

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
