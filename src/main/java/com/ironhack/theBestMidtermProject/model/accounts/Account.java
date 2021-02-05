package com.ironhack.theBestMidtermProject.model.accounts;

import com.ironhack.theBestMidtermProject.model.users.*;
import com.ironhack.theBestMidtermProject.utils.*;

import javax.persistence.*;

//todo NO PARECE QUE MONEY LE SIENTE MUY BIEN A MYSQL

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Account {

//    todo pregunta si id puede ser un generated value o normalmente los dígitos tienen algún sentido
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    TODO TOMO ESTO COMO UN BIGDECIMAL? Y SI ES ASÍ, QUE TIPO DE DATO USO EN MYSQL? Y SI ES DECIMAL, CUANTOS DIGITOS USO?
    private Money balance;
    private User primaryOwner;

//    Empty constructor
    public Account() {
    }

//    Constructor with all parameters
    public Account(Money balance, User primaryOwner) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
    }

//    Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public User getPrimaryOwner() {
        return primaryOwner;
    }

    public void setPrimaryOwner(User primaryOwner) {
        this.primaryOwner = primaryOwner;
    }
}
