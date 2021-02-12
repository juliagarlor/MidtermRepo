package com.ironhack.theBestMidtermProject.model;

import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.datatype.jsr310.deser.*;
import com.fasterxml.jackson.datatype.jsr310.ser.*;
import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;

import javax.persistence.*;
import java.time.*;


@Entity
@PrimaryKeyJoinColumn(name = "id")
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "emisor_id")
    private Account emisor;
    @ManyToOne
    @JoinColumn(name = "receptor_id")
    private Account receptor;
    @Embedded
    @AttributeOverrides(value ={
            @AttributeOverride(name = "amount", column = @Column(name = "amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "currency"))
    })
    private Money amount;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime moment;

//    Empty constructor
    public Transactions() {
    }

//    Controller with all parameters
    public Transactions(Account emisorId, Account receptorId, Money amount, LocalDateTime moment) {
        this.emisor = emisorId;
        this.receptor = receptorId;
        this.amount = amount;
        this.moment = moment;
    }

//    Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getEmisorId() {
        return emisor;
    }

    public void setEmisorId(Account emisorId) {
        this.emisor = emisorId;
    }

    public Account getReceptorId() {
        return receptor;
    }

    public void setReceptorId(Account receptorId) {
        this.receptor = receptorId;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public LocalDateTime getMoment() {
        return moment;
    }

    public void setMoment(LocalDateTime moment) {
        this.moment = moment;
    }
}
