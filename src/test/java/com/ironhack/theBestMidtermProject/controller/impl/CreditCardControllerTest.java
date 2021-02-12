package com.ironhack.theBestMidtermProject.controller.impl;

import com.fasterxml.jackson.databind.*;
import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.repository.accounts.*;
import com.ironhack.theBestMidtermProject.repository.users.*;
import com.ironhack.theBestMidtermProject.security.*;
import com.ironhack.theBestMidtermProject.service.impl.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;
import com.ironhack.theBestMidtermProject.utils.enums.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.*;
import org.springframework.web.context.*;

import java.math.*;
import java.time.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CreditCardControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private CreditCardAccountRepository creditAccountRepository;

    @Autowired
    private AccountHolderService accountHolderService;

    private Transformer transformer = new Transformer();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();

        NameDTO name = transformer.assembleNameDTO("Rodriguez", "Cayetano", "Jesús", Salutation.Mr);
        AddressDTO primaryAddress = transformer.assembleAddressDTO(1, "Castilla", "Madrid", "España");
        AccountHolderDTO primaryOwner = transformer.assembleAccountHolderDTO(name,18, "contraseña",
                LocalDateTime.of(2002, 5, 20, 18, 40, 00), primaryAddress, null);
        accountHolderService.createAccountHolder(primaryOwner);

        NameDTO name2 = transformer.assembleNameDTO("Rodriguez", "María", "Jesús", Salutation.Mrs);
        AddressDTO primaryAddress2 = transformer.assembleAddressDTO(1, "Castilla", "Madrid", "España");
        AccountHolderDTO secondaryOwner = transformer.assembleAccountHolderDTO(name2, 40, "contraseña2",
                LocalDateTime.of(1980, 5, 20, 18, 40, 00), primaryAddress2, null);
        accountHolderService.createAccountHolder(secondaryOwner);
    }

    @AfterEach
    void tearDown() {
        creditAccountRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    void checkAccount_validLogin_Balance() throws Exception {
//        Creating an account
        createCreditAccount();

//        Setting creation to two months ago:
        CreditCardAccount test = creditAccountRepository.findAll().get(0);
        test.setLastInterestRateApplied(test.getLastInterestRateApplied().minusMonths(2));
        creditAccountRepository.save(test);

        CustomUserDetails user = new CustomUserDetails(accountHolderRepository.findAll().get(0));

        MvcResult result = mockMvc.perform(
                get("/check/credit-card/" + test.getId()).with(user(user)))
                .andExpect(status().isOk()).andReturn();

         assertEquals(new BigDecimal("1021.58"), creditAccountRepository.findAll().get(0).getBalance().getAmount());
    }

    @Test
    void checkAccount_invalidLogin_Balance() throws Exception {
//        Creating an account
        createCreditAccount();

//        Setting the secondaryOwner to null:
        CreditCardAccount test = creditAccountRepository.findAll().get(0);
        test.setSecondaryOwner(null);
        creditAccountRepository.save(test);

        CustomUserDetails user = new CustomUserDetails(accountHolderRepository.findAll().get(1));

        MvcResult result = mockMvc.perform(
                get("/check/credit-card/" + test.getId()).with(user(user)))
                .andExpect(status().isUnauthorized()).andReturn();
        assertTrue(result.getResolvedException().toString().contains("You are not authorized to see this data"));
    }

    @Test
    void createCreditAccount_validValues_CreditAccount() throws Exception {
        assertEquals(0, creditAccountRepository.findAll().size());

        BigDecimal balance = new BigDecimal("1000");
        long secondaryOwnerId = accountHolderRepository.findAll().get(1).getId();
        BigDecimal monthlyMaintenanceFee = new BigDecimal("6");
        CreditAcDTO creditAcDTO = transformer.assembleCreditAcDTO(balance, secondaryOwnerId, null, monthlyMaintenanceFee, null);

        String body = objectMapper.writeValueAsString(creditAcDTO);
        System.out.println(body);
        MvcResult result = mockMvc.perform(
                post("/new/credit-account/" + accountHolderRepository.findAll().get(0).getId())
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

//        Make sure that the newly created account has been linked to Cayetano
        assertTrue(result.getResponse().getContentAsString().contains("Cayetano"));
//        Make sure that it has been also linked to the secondary owner
        assertEquals(creditAccountRepository.findAll().get(0).getId(),
                accountHolderRepository.findAll().get(1).getSecondaryAccounts().get(0).getId());
//        Make sure that this new account has been stored in the repository
        assertEquals(1, creditAccountRepository.findAll().size());
    }

    @Test
    void createCreditAccount_invalidValues_CreditAccount() throws Exception {

        BigDecimal balance = new BigDecimal("1000");
        long secondaryOwnerId = accountHolderRepository.findAll().get(1).getId();
        BigDecimal monthlyMaintenanceFee = new BigDecimal("6");
        BigDecimal creditLimit = new BigDecimal("50");
        CreditAcDTO creditAcDTO = transformer.assembleCreditAcDTO(balance, secondaryOwnerId, creditLimit, monthlyMaintenanceFee, null);

        String body = objectMapper.writeValueAsString(creditAcDTO);
        System.out.println(body);
        MvcResult result = mockMvc.perform(
                post("/new/credit-account/" + accountHolderRepository.findAll().get(0).getId())
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertTrue(result.getResolvedException().toString().contains("The minimum credit limit is 100"));
        assertEquals(0, creditAccountRepository.findAll().size());
    }

    void createCreditAccount() throws Exception {
        BigDecimal balance = new BigDecimal("1000");
        long secondaryOwnerId = accountHolderRepository.findAll().get(1).getId();
        BigDecimal monthlyMaintenanceFee = new BigDecimal("6");
        CreditAcDTO creditAcDTO = transformer.assembleCreditAcDTO(balance, secondaryOwnerId, null,
                monthlyMaintenanceFee, null);

        String body = objectMapper.writeValueAsString(creditAcDTO);
        mockMvc.perform(
                post("/new/credit-account/" + accountHolderRepository.findAll().get(0).getId())
                        .content(body).contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void addAmount_validValues_Account() throws Exception {
        createCreditAccount();

        Money amount = new Money(new BigDecimal("20"));
        CreditCardAccount test = creditAccountRepository.findAll().get(0);

        String body = objectMapper.writeValueAsString(amount);
        System.out.println(body);
        MvcResult result = mockMvc.perform(
                patch("/admin/credit-card/" + test.getId() + "/increaseBalance")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(new BigDecimal("1020.00"), creditAccountRepository.findAll().get(0).getBalance().getAmount());
    }

    @Test
    void addAmount_negativeValues_BadRequest() throws Exception {
        createCreditAccount();

        Money amount = new Money(new BigDecimal("-20"));
        CreditCardAccount test = creditAccountRepository.findAll().get(0);

        String body = objectMapper.writeValueAsString(amount);
        System.out.println(body);
        MvcResult result = mockMvc.perform(
                patch("/admin/credit-card/" + test.getId() + "/increaseBalance")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertTrue(result.getResolvedException().toString().contains("Please, introduce a positive amount"));
//        Make sure the amount has been added to the test account
        assertEquals(new BigDecimal("1000.00"), creditAccountRepository.findAll().get(0).getBalance().getAmount());
    }

    @Test
    void subtractAmount_amountSmallerThanBalance_Account() throws Exception {
        createCreditAccount();

        Money amount = new Money(new BigDecimal("20"));
        CreditCardAccount test = creditAccountRepository.findAll().get(0);

        String body = objectMapper.writeValueAsString(amount);
        System.out.println(body);
        MvcResult result = mockMvc.perform(
                patch("/admin/credit-card/" + test.getId() + "/decreaseBalance")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(new BigDecimal("980.00"), creditAccountRepository.findAll().get(0).getBalance().getAmount());
    }

    @Test
    void subtractAmount_amountBiggerThanBalance_NotAcceptable() throws Exception {
        createCreditAccount();

        Money amount = new Money(new BigDecimal("1020"));
        CreditCardAccount test = creditAccountRepository.findAll().get(0);

        String body = objectMapper.writeValueAsString(amount);
        System.out.println(body);
        MvcResult result = mockMvc.perform(
                patch("/admin/credit-card/" + test.getId() + "/decreaseBalance")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable())
                .andReturn();

        assertTrue(result.getResolvedException().toString().contains("As broke as Donald Trump"));
        assertEquals(new BigDecimal("1000.00"), creditAccountRepository.findAll().get(0).getBalance().getAmount());
    }


}