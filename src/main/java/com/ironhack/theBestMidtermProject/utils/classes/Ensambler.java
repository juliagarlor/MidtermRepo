package com.ironhack.theBestMidtermProject.utils.classes;

import com.ironhack.theBestMidtermProject.utils.dtos.*;
import com.ironhack.theBestMidtermProject.utils.enums.*;

import java.math.*;
import java.time.*;

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

    public NameDTO ensambleNameDTO(String lastName, String firstName, String middleName, Salutation salutation){
        NameDTO output = new NameDTO();
        output.setLastName(lastName);
        output.setFirstName(firstName);
        output.setMiddleName(middleName);
        output.setSalutation(salutation);
        return output;
    }

    public AddressDTO ensambleAddressDTO(int num, String street, String city, String country){
        AddressDTO output = new AddressDTO();
        output.setNumber(num);
        output.setStreet(street);
        output.setCity(city);
        output.setCountry(country);
        return output;
    }

    public AccountHolderDTO ensambleAccountHolderDTO(NameDTO nameDTO, int age, String password, LocalDateTime dateOfBirth,
                                                     AddressDTO primaryAddress, AddressDTO mailingAddress){
        AccountHolderDTO output = new AccountHolderDTO();
        output.setNameDTO(nameDTO);
        output.setAge(age);
        output.setPassword(password);
        output.setDateOfBirth(dateOfBirth);
        output.setPrimaryAddressDTO(primaryAddress);
        output.setMailingAddressDTO(mailingAddress);
        return output;
    }

    public AdminDTO ensambleAdminDTO(NameDTO nameDTO, int age, String password){
        AdminDTO output = new AdminDTO();
        output.setNameDTO(nameDTO);
        output.setAge(age);
        output.setPassword(password);
        return output;
    }

    public ThirdPartyDTO ensambleThirdPartyDTO(NameDTO nameDTO, int age, String hashKey, String password){
        ThirdPartyDTO output = new ThirdPartyDTO();
        output.setNameDTO(nameDTO);
        output.setAge(age);
        output.setHashedKey(hashKey);
        output.setPassword(password);
        return output;
    }

    public CheckingAcDTO ensambleCheckingAcDTO(BigDecimal balance, String secretKey, long secondaryOwnerId){
        CheckingAcDTO output = new CheckingAcDTO();
        output.setBalance(balance);
        output.setSecretKey(secretKey);
        output.setSecondaryOwner(secondaryOwnerId);
        return output;
    }

    public CreditAcDTO ensambleCreditAcDTO(BigDecimal balance, long secondaryOwnerId, BigDecimal creditLimit,
                                           BigDecimal monthlyMaintenanceFee, BigDecimal interestRate){
        CreditAcDTO output = new CreditAcDTO();
        output.setBalance(balance);
        output.setSecondaryOwnerId(secondaryOwnerId);
        output.setCreditLimit(creditLimit);
        output.setMonthlyMaintenanceFee(monthlyMaintenanceFee);
        output.setInterestRate(interestRate);
        return output;
    }

    public SavingsAcDTO ensambleSavingsDTO(BigDecimal balance, BigDecimal minimumBalance, String secretKey,
                                           long secondaryOwnerId, BigDecimal interestRate){
        SavingsAcDTO output = new SavingsAcDTO();
        output.setBalance(balance);
        output.setMinimumBalance(minimumBalance);
        output.setSecretKey(secretKey);
        output.setSecondaryOwnerId(secondaryOwnerId);
        output.setInterestRate(interestRate);
        return output;
    }
}
