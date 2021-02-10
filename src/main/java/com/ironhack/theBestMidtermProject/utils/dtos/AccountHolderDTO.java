package com.ironhack.theBestMidtermProject.utils.dtos;


import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.datatype.jsr310.deser.*;
import com.fasterxml.jackson.datatype.jsr310.ser.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.enums.*;

import javax.validation.*;
import javax.validation.constraints.*;
import java.time.*;
import java.util.*;

public class AccountHolderDTO {
    @Valid
    private NameDTO nameDTO;
    @Min(value = 18, message = "The user must be of age.")
    private int age;
    @Size(min = 7, message = "The password key must have at least 7 digits.")
    private String password;
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime dateOfBirth;
    @Valid
    private AddressDTO primaryAddressDTO;
    @Valid
    private AddressDTO mailingAddressDTO;

//    Getters and Setters
    public NameDTO getNameDTO() {
        return nameDTO;
    }

    public void setNameDTO(NameDTO nameDTO) {
        this.nameDTO = nameDTO;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public LocalDateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public AddressDTO getPrimaryAddressDTO() {
        return primaryAddressDTO;
    }

    public void setPrimaryAddressDTO(AddressDTO primaryAddressDTO) {
        this.primaryAddressDTO = primaryAddressDTO;
    }

    public AddressDTO getMailingAddressDTO() {
        return mailingAddressDTO;
    }

    public void setMailingAddressDTO(AddressDTO mailingAddressDTO) {
        this.mailingAddressDTO = mailingAddressDTO;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
