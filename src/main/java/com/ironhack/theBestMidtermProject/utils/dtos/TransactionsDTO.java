package com.ironhack.theBestMidtermProject.utils.dtos;

import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.datatype.jsr310.deser.*;
import com.fasterxml.jackson.datatype.jsr310.ser.*;

import javax.validation.constraints.*;
import java.math.*;
import java.time.*;

public class TransactionsDTO {

    @Min(value = 0, message = "Accounts identifiers can not be negative numbers")
    private Long emisor;
    @Min(value = 0, message = "Accounts identifiers can not be negative numbers")
    private Long receptor;
    @DecimalMin(value = "0", message = "Please, introduce a positive quantity.")
    private BigDecimal amount;
    @PastOrPresent
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime moment;

//    Getters and Setters

    public Long getEmisor() {
        return emisor;
    }

    public void setEmisor(Long emisor) {
        this.emisor = emisor;
    }

    public Long getReceptor() {
        return receptor;
    }

    public void setReceptor(Long receptor) {
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
