package com.ironhack.theBestMidtermProject.model;

import com.ironhack.theBestMidtermProject.utils.classes.*;

import javax.persistence.*;
import java.time.*;

@Entity
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "emisor_id")
    private long emisorId;
    @ManyToOne
    @JoinColumn(name = "receptor_id")
    private long receptorId;
    private Money amount;
    private LocalDateTime moment;

//    Empty constructor
    public Transactions() {
    }

//    Controller with all parameters
    public Transactions(long id, long emisorId, long receptorId, Money amount, LocalDateTime moment) {
        this.id = id;
        this.emisorId = emisorId;
        this.receptorId = receptorId;
        this.amount = amount;
        this.moment = moment;
    }

//    Getters y Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEmisorId() {
        return emisorId;
    }

    public void setEmisorId(long emisorId) {
        this.emisorId = emisorId;
    }

    public long getReceptorId() {
        return receptorId;
    }

    public void setReceptorId(long receptorId) {
        this.receptorId = receptorId;
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
