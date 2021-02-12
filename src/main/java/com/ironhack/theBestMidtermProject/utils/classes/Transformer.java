package com.ironhack.theBestMidtermProject.utils.classes;

import com.ironhack.theBestMidtermProject.model.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;
import com.ironhack.theBestMidtermProject.utils.enums.*;

import java.math.*;
import java.time.*;

public class Transformer {

    public Name assembleName(NameDTO nameDTO){
        String firstName = nameDTO.getFirstName();
        String lastName = nameDTO.getLastName();
        String middleName = nameDTO.getMiddleName();
        Salutation salutation = nameDTO.getSalutation();
        return new Name(lastName,firstName,middleName,salutation);
    }

    public Address assembleAddress(AddressDTO addressDTO){
        int num = addressDTO.getNumber();
        String street = addressDTO.getStreet();
        String city = addressDTO.getCity();
        String country = addressDTO.getCountry();
        return new Address(num, street, city, country);
    }

    public NameDTO assembleNameDTO(String lastName, String firstName, String middleName, Salutation salutation){
        NameDTO output = new NameDTO();
        output.setLastName(lastName);
        output.setFirstName(firstName);
        output.setMiddleName(middleName);
        output.setSalutation(salutation);
        return output;
    }

    public AddressDTO assembleAddressDTO(int num, String street, String city, String country){
        AddressDTO output = new AddressDTO();
        output.setNumber(num);
        output.setStreet(street);
        output.setCity(city);
        output.setCountry(country);
        return output;
    }

    public AccountHolderDTO assembleAccountHolderDTO(NameDTO nameDTO, int age, String password, LocalDateTime dateOfBirth,
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

    public AdminDTO assembleAdminDTO(NameDTO nameDTO, int age, String password){
        AdminDTO output = new AdminDTO();
        output.setNameDTO(nameDTO);
        output.setAge(age);
        output.setPassword(password);
        return output;
    }

    public ThirdPartyDTO assembleThirdPartyDTO(NameDTO nameDTO, int age, String hashKey, String password){
        ThirdPartyDTO output = new ThirdPartyDTO();
        output.setNameDTO(nameDTO);
        output.setAge(age);
        output.setHashedKey(hashKey);
        output.setPassword(password);
        return output;
    }

    public CheckingAcDTO assembleCheckingAcDTO(BigDecimal balance, String secretKey, Long secondaryOwnerId){
        CheckingAcDTO output = new CheckingAcDTO();
        output.setBalance(balance);
        output.setSecretKey(secretKey);
        output.setSecondaryOwner(secondaryOwnerId);
        return output;
    }

    public CreditAcDTO assembleCreditAcDTO(BigDecimal balance, Long secondaryOwnerId, BigDecimal creditLimit,
                                           BigDecimal monthlyMaintenanceFee, BigDecimal interestRate){
        CreditAcDTO output = new CreditAcDTO();
        output.setBalance(balance);
        output.setSecondaryOwnerId(secondaryOwnerId);
        output.setCreditLimit(creditLimit);
        output.setMonthlyMaintenanceFee(monthlyMaintenanceFee);
        output.setInterestRate(interestRate);
        return output;
    }

    public SavingsAcDTO assembleSavingsDTO(BigDecimal balance, BigDecimal minimumBalance, String secretKey,
                                           Long secondaryOwnerId, BigDecimal interestRate){
        SavingsAcDTO output = new SavingsAcDTO();
        output.setBalance(balance);
        output.setMinimumBalance(minimumBalance);
        output.setSecretKey(secretKey);
        output.setSecondaryOwnerId(secondaryOwnerId);
        output.setInterestRate(interestRate);
        return output;
    }

    public TransactionsDTO assembleTransactionsDTO(Long emisor, Long receptor,BigDecimal amount, LocalDateTime moment){
        TransactionsDTO output = new TransactionsDTO();
        output.setEmisor(emisor);
        output.setReceptor(receptor);
        output.setAmount(amount);
        output.setMoment(moment);
        return output;
    }
}
