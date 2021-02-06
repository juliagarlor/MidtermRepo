package com.ironhack.theBestMidtermProject.model.accounts;

import com.ironhack.theBestMidtermProject.model.users.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;

import javax.persistence.*;
import java.math.*;

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

//    Peculiar methods

    public abstract boolean checkPassword(String password);

//    An accountHolder should have the ability to transfer money from any of their accounts to other accounts.
//    The transfer method will take a name and an id of the target account, and the desired amount to transfer,
//    and will provide this amount as the output so the second account can receive it

/*    public Money transfer(String name, long id, Money amount){
//        Check if the balance of this account is enough
        if (amount.getAmount().compareTo(balance.getAmount()) >= 0){
            balance.getAmount().subtract(amount.getAmount());
        }else {
//            if not, throw an exception
            throw new IllegalArgumentException("The transferred amount is too large. Please, choose other amount or try from other account");
        }
        return amount;
    }*/

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
