package com.ironhack.theBestMidtermProject.utils.dtos;

import com.fasterxml.jackson.databind.annotation.*;
import com.fasterxml.jackson.datatype.jsr310.deser.*;
import com.fasterxml.jackson.datatype.jsr310.ser.*;

import javax.validation.*;
import javax.validation.constraints.*;
import java.time.*;

/*AccountHolderDTO has a nameDTO, an age, a password, a date of birth, a primary address and an optional secondary address.
* Everything well constrained by limits and validations*/

public class AccountHolderDTO {
    @Valid
    private NameDTO nameDTO;
    @NotNull(message = "You do not look so old. Please make an effort.")
    @Min(value = 18, message = "The user must be of age.")
    private int age;

//    password must have a minimum size of 7 characters to be secure
    @NotNull(message = "Can you be a little more creative with your password?")
    @Size(min = 7, message = "The password key must have at least 7 digits.")
    private String password;

//    dateOfBirth can not be a date from the future
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @PastOrPresent
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
