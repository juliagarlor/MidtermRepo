package com.ironhack.theBestMidtermProject.utils.dtos;

import javax.validation.*;
import javax.validation.constraints.*;

public class ThirdPartyDTO {

    @Valid
    private NameDTO nameDTO;

//    I do not know the rules in other banks, but in ours only people over 18 can be registered
    @NotNull(message = "Unless you are Mr.Burns, please, make an effort and remember your age.")
    @Min(value = 18, message = "The user must be of age.")
    private int age;

    @NotNull(message = "The hashed key can not be null.")
    @Size(min = 7, message = "The hashed key must have at least 7 digits.")
    private String hashedKey;
    @NotNull(message = "The password can not be null.")
    @Size(min = 7, message = "The password key must have at least 7 digits.")
    private String password;

//    Getters and setters
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

    public String getHashedKey() {
        return hashedKey;
    }

    public void setHashedKey(String hashedKey) {
        this.hashedKey = hashedKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
