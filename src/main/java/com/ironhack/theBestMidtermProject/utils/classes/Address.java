package com.ironhack.theBestMidtermProject.utils.classes;

import javax.persistence.*;

//Address class has a number(of the building), a street, a city, and a country.

@Embeddable
public class Address {

    private int number;
    private String street;
    private String city;
    private String country;

//    Empty constructor
    public Address() {
    }

//    Constructor with all parameters
    public Address(int number, String street, String city, String country) {
        this.number = number;
        this.street = street;
        this.city = city;
        this.country = country;
    }

//    Getters y setters
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
