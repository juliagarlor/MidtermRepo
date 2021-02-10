package com.ironhack.theBestMidtermProject.utils.dtos;

import javax.validation.constraints.*;
import java.math.*;
import java.time.*;

public class TransactionsDTO {

    @Min(value = 0, message = "Accounts identifiers can not be negative numbers")
    private long emisor;
    @Min(value = 0, message = "Accounts identifiers can not be negative numbers")
    private long receptor;
    @DecimalMin(value = "0", message = "Please, introduce a positive quantity.")
    private BigDecimal amount;
    @PastOrPresent
    private LocalDateTime moment;

//    Getters and Setters

    public long getEmisor() {
        return emisor;
    }

    public void setEmisor(long emisor) {
        this.emisor = emisor;
    }

    public long getReceptor() {
        return receptor;
    }

    public void setReceptor(long receptor) {
        this.receptor = receptor;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getMoment() {
        return moment;
    }

    public void setMoment(LocalDateTime moment) {
        this.moment = moment;
    }
}
