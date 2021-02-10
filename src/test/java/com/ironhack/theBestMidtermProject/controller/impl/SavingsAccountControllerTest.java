package com.ironhack.theBestMidtermProject.controller.impl;

import com.fasterxml.jackson.databind.*;
import com.ironhack.theBestMidtermProject.repository.accounts.*;
import com.ironhack.theBestMidtermProject.repository.users.*;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class SavingsAccountControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    @Autowired
    private AccountHolderService accountHolderService;

    private Transformer transformer = new Transformer();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        NameDTO name = transformer.ensambleNameDTO("Rodriguez", "Cayetano", "Jesús", Salutation.Mr);
        AddressDTO primaryAddress = transformer.ensambleAddressDTO(1, "Castilla", "Madrid", "España");
        AccountHolderDTO primaryOwner = transformer.ensambleAccountHolderDTO(name,18, "contraseña",
                LocalDateTime.of(2002, 5, 20, 18, 40, 00), primaryAddress, null);
        accountHolderService.createAccountHolder(primaryOwner);

        NameDTO name2 = transformer.ensambleNameDTO("Rodriguez", "María", "Jesús", Salutation.Mrs);
        AddressDTO primaryAddress2 = transformer.ensambleAddressDTO(1, "Castilla", "Madrid", "España");
        AccountHolderDTO secondaryOwner = transformer.ensambleAccountHolderDTO(name2, 40, "contraseña2",
                LocalDateTime.of(1980, 5, 20, 18, 40, 00), primaryAddress2, null);
        accountHolderService.createAccountHolder(secondaryOwner);
    }

    @AfterEach
    void tearDown() {
        savingsAccountRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    void createSavingsAccount_validValues_SavingsAccount() throws Exception {
        assertEquals(0, savingsAccountRepository.findAll().size());

        BigDecimal balance = new BigDecimal("1000");
        long secondaryOwnerId = accountHolderRepository.findAll().get(1).getId();
        BigDecimal minimumBalance = new BigDecimal("100");
        BigDecimal interestRate = new BigDecimal("0.4");

        SavingsAcDTO savingsAcDTO = transformer.ensambleSavingsDTO(balance, minimumBalance, "algunoTieneBarco",
                secondaryOwnerId, interestRate);

        String body = objectMapper.writeValueAsString(savingsAcDTO);
        System.out.println(body);
        MvcResult result = mockMvc.perform(
                post("/new/savings-account/" + accountHolderRepository.findAll().get(0).getId())
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

//        Make sure that the newly created account has been linked to Cayetano
        assertTrue(result.getResponse().getContentAsString().contains("Cayetano"));
//        Make sure that it has been also linked to the secondary owner
        assertEquals(savingsAccountRepository.findAll().get(0).getId(),
                accountHolderRepository.findAll().get(1).getSecondaryAccounts().get(0).getId());
//        Make sure that this new account has been stored in the repository
        assertEquals(1, savingsAccountRepository.findAll().size());
    }

    @Test
    void createSavingsAccount_invalidValues_Exception() throws Exception {

        BigDecimal balance = new BigDecimal("1000");
        long secondaryOwnerId = accountHolderRepository.findAll().get(1).getId();
        BigDecimal minimumBalance = new BigDecimal("99");
        BigDecimal interestRate = new BigDecimal("0.4");
        SavingsAcDTO savingsAcDTO = transformer.ensambleSavingsDTO(balance, minimumBalance, "siempreTresBotonesDesabrochados", secondaryOwnerId, interestRate);

        String body = objectMapper.writeValueAsString(savingsAcDTO);
        System.out.println(body);
        MvcResult result = mockMvc.perform(
                post("/new/savings-account/" + accountHolderRepository.findAll().get(0).getId())
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        System.out.println(result.getResolvedException());
        assertTrue(result.getResolvedException().toString().contains("The minimum balance for this account must be higher than 100."));
        assertEquals(0, savingsAccountRepository.findAll().size());
    }
}