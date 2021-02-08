package com.ironhack.theBestMidtermProject.utils.dtos;


import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.enums.*;

import javax.validation.*;
import javax.validation.constraints.*;
import java.time.*;
import java.util.*;

public class AccountHolderDTO {
    private Name name;
    @Min(value = 18, message = "The user must be of age.")
    private int age;
    private LocalDateTime dateOfBirth;
    @Valid
    private Address primaryAddress;
    @Valid
    private Address mailingAddress;

//    Constructor without mailingAddress

    public AccountHolderDTO(@Valid NameDTO nameDTO, @Min(value = 18, message = "The user must be of age.") int age,
                            LocalDateTime dateOfBirth, @Valid AddressDTO primaryAddressDTO) {
        setName(nameDTO);
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        setPrimaryAddress(primaryAddressDTO);
    }

//    Constructor with all parameters
    public AccountHolderDTO(@Valid NameDTO nameDTO, @Min(value = 18, message = "The user must be of age.") int age,
                            LocalDateTime dateOfBirth, @Valid AddressDTO primaryAddressDTO, @Valid AddressDTO mailingAddressDTO) {
        setName(nameDTO);
        this.age = age;
        this.dateOfBirth = dateOfBirth;
        setPrimaryAddress(primaryAddressDTO);
        setMailingAddress(mailingAddressDTO);
    }

    public Name getName() {
        return name;
    }

    public void setName(NameDTO nameDTO) {
        String firstName = nameDTO.getFirstName();
        String lastName = nameDTO.getLastName();
        String middleName = nameDTO.getMiddleName();
        Salutation salutation = nameDTO.getSalutation();
        this.name = new Name(lastName, firstName, middleName, salutation);
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

    public Address getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(AddressDTO primaryAddressDTO) {
        int num = primaryAddressDTO.getNumber();
        String street = primaryAddressDTO.getStreet();
        String city = primaryAddressDTO.getCity();
        String country = primaryAddressDTO.getCountry();
        this.primaryAddress = new Address(num, street, city, country);
    }

    public Address getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(AddressDTO mailingAddressDTO) {
        int num = mailingAddressDTO.getNumber();
        String street = mailingAddressDTO.getStreet();
        String city = mailingAddressDTO.getCity();
        String country = mailingAddressDTO.getCountry();
        this.mailingAddress = new Address(num, street, city, country);
    }
}
