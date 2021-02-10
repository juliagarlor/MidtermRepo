package com.ironhack.theBestMidtermProject.utils.dtos;

import javax.validation.constraints.*;

public class AddressDTO {
    @Min(value = 0, message = "The number must be a positive value.")
    private int number;
    @NotNull(message = "Please, introduce the street name.")
    private String street;
    @NotNull(message = "Please, introduce the name of the city.")
    private String city;
    @NotNull(message = "Please, introduce the name of a valid country.")
    private String country;

//Getters and Setters
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
