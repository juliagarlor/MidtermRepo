package com.ironhack.theBestMidtermProject.model.accounts;

import com.ironhack.theBestMidtermProject.model.users.*;
import com.ironhack.theBestMidtermProject.utils.*;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {

//    todo pregunta si id puede ser un generated value o normalmente los dígitos tienen algún sentido
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    TODO TOMO ESTO COMO UN BIGDECIMAL? Y SI ES ASÍ, QUE TIPO DE DATO USO EN MYSQL? Y SI ES DECIMAL, CUANTOS DIGITOS USO?
    @Embedded
    @AttributeOverrides(value ={
            @AttributeOverride(name = "amount", column = @Column(name = "balance")),
            @AttributeOverride(name = "currency", column = @Column(name = "currency"))
    })
    private Money balance;
    @ManyToOne
    @JoinColumn(name = "primary_owner_id")
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
