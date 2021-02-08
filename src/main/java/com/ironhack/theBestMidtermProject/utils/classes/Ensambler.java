package com.ironhack.theBestMidtermProject.utils.classes;

import com.ironhack.theBestMidtermProject.utils.dtos.*;
import com.ironhack.theBestMidtermProject.utils.enums.*;

public class Ensambler {

    public Name ensambleName(NameDTO nameDTO){
        String firstName = nameDTO.getFirstName();
        String lastName = nameDTO.getLastName();
        String middleName = nameDTO.getMiddleName();
        Salutation salutation = nameDTO.getSalutation();
        return new Name(lastName,firstName,middleName,salutation);
    }

    public Address ensambleAddress(AddressDTO addressDTO){
        int num = addressDTO.getNumber();
        String street = addressDTO.getStreet();
        String city = addressDTO.getCity();
        String country = addressDTO.getCountry();
        return new Address(num, street, city, country);
    }
}
