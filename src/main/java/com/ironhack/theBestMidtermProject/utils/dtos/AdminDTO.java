package com.ironhack.theBestMidtermProject.utils.dtos;

import com.ironhack.theBestMidtermProject.utils.classes.*;

import javax.validation.*;
import javax.validation.constraints.*;

public class AdminDTO {
    @Valid
    private NameDTO nameDTO;
    @Min(value = 18, message = "The user must be of age.")
    private int age;
    @Size(min = 7, message = "The password key must have at least 7 digits.")
    private String password;

    public AdminDTO() {
    }

    public AdminDTO(@Valid NameDTO nameDTO, @Min(value = 18, message = "The user must be of age.") int age,
                    @Size(min = 7, message = "The password key must have at least 7 digits.") String password) {
        this.nameDTO = nameDTO;
        this.age = age;
        this.password = password;
    }

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
