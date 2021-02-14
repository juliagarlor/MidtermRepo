package com.ironhack.theBestMidtermProject.utils.dtos;

import javax.validation.constraints.*;

public class AddressDTO {

//    A street number can not be negative
    @Min(value = 0, message = "The number must be a positive value.")
    private int number;

//    Each address must have a street name
    @NotEmpty(message = "Please, introduce the street name.")
    @NotNull(message = "Please, introduce the street name.")
    private String street;

//    And a city
    @NotEmpty(message = "Please, introduce the name of the city.")
    @NotNull(message = "Please, introduce the name of the city.")
    private String city;

//    And this city should be in a country
    @NotEmpty(message = "Please, introduce the name of a valid country.")
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
