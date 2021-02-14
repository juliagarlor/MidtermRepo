package com.ironhack.theBestMidtermProject.utils.dtos;

import javax.validation.*;
import javax.validation.constraints.*;

public class AdminDTO {
    @Valid
    private NameDTO nameDTO;

//    users must be over 18 in order to be registered
    @NotNull(message = "You do not look so old. Try again.")
    @Min(value = 18, message = "The user must be of age.")
    private int age;

//    passwords must be longer than 7 characters to be secure
    @NotNull(message = "Your password can not be null, that would be too easy.")
    @Size(min = 7, message = "The password key must have at least 7 digits.")
    private String password;

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
