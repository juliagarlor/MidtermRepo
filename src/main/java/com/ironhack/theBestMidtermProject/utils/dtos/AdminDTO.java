package com.ironhack.theBestMidtermProject.utils.dtos;

import com.ironhack.theBestMidtermProject.utils.classes.*;

import javax.validation.*;
import javax.validation.constraints.*;

public class AdminDTO {
    @Valid
    private NameDTO nameDTO;
    @Min(value = 18, message = "The user must be of age.")
    private int age;

    public AdminDTO(@Valid NameDTO nameDTO, @Min(value = 18, message = "The user must be of age.") int age) {
        this.nameDTO = nameDTO;
        this.age = age;
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
}
