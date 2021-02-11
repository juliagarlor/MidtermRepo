package com.ironhack.theBestMidtermProject.controller.impl;

import com.fasterxml.jackson.databind.*;
import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.model.users.*;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AccountControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CheckingAccountRepository checkingAccountRepository;

    @Autowired
    private StudentCheckingAccountRepository studentAccountRepository;

    @Autowired
    private AccountHolderService accountHolderService;

    private Transformer transformer = new Transformer();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

//        Create an accountHolder
        NameDTO name = transformer.assembleNameDTO("Rodriguez", "Cayetano", "Jesús", Salutation.Mr);
        AddressDTO primaryAddress = transformer.assembleAddressDTO(1, "Castilla", "Madrid", "España");
        AccountHolderDTO primaryOwner = transformer.assembleAccountHolderDTO(name,25, "contraseña",
                LocalDateTime.of(2002, 5, 20, 18, 40, 00), primaryAddress, null);
        accountHolderService.createAccountHolder(primaryOwner);

//        Another one
        NameDTO name2 = transformer.assembleNameDTO("Rodriguez", "María", "Jesús", Salutation.Mrs);
        AddressDTO primaryAddress2 = transformer.assembleAddressDTO(1, "Castilla", "Madrid", "España");
        AccountHolderDTO secondaryOwner = transformer.assembleAccountHolderDTO(name2, 40, "contraseña2",
                LocalDateTime.of(1980, 5, 20, 18, 40, 00), primaryAddress2, null);
        accountHolderService.createAccountHolder(secondaryOwner);

//        And a test checkingAccount for the add and subtractBalance method
        Money balance = new Money(new BigDecimal("1000"));
        CheckingAccount checkingAccount = new CheckingAccount(balance, Status.ACTIVE, accountHolderRepository.findAll().get(0),
                accountHolderRepository.findAll().get(1), "comoLoPasamos");
        checkingAccountRepository.save(checkingAccount);
    }

    @AfterEach
    void tearDown() {
        accountRepository.deleteAll();
        checkingAccountRepository.deleteAll();
        studentAccountRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    void createCheckAccount_validValues_CheckingAccount() throws Exception {
        assertEquals(1, checkingAccountRepository.findAll().size());

        BigDecimal balance = new BigDecimal("1000");
        long secondaryOwnerId = accountHolderRepository.findAll().get(1).getId();
        CheckingAcDTO checkingAcDTO = transformer.assembleCheckingAcDTO(balance, "menudoPelazo", secondaryOwnerId);

        String body = objectMapper.writeValueAsString(checkingAcDTO);
        System.out.println(body);
        MvcResult result = mockMvc.perform(
                post("/new/checking-account/" + accountHolderRepository.findAll().get(0).getId())
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());

//        Make sure that the newly created account has been linked to Cayetano
        assertTrue(result.getResponse().getContentAsString().contains("Cayetano"));
//        Make sure that it has been also linked to the secondary owner
        assertEquals(accountRepository.findAll().get(0).getId(),
                accountHolderRepository.findAll().get(1).getSecondaryAccounts().get(0).getId());
//        Make sure that this new account has been stored in the repository
        assertEquals(2, accountRepository.findAll().size());
    }

    @Test
    void createCheckAccount_validValues_StudentAccount() throws Exception {
        assertEquals(1, checkingAccountRepository.findAll().size());

//        Reset the age of Cayetano to be young, as he would wish
        AccountHolder primaryOwner = accountHolderRepository.findAll().get(0);
        primaryOwner.setAge(18);
        accountHolderRepository.save(primaryOwner);

        BigDecimal balance = new BigDecimal("1000");
        long secondaryOwnerId = accountHolderRepository.findAll().get(1).getId();
        CheckingAcDTO checkingAcDTO = transformer.assembleCheckingAcDTO(balance, "menudoPelazo", secondaryOwnerId);

        String body = objectMapper.writeValueAsString(checkingAcDTO);
        System.out.println(body);
        MvcResult result = mockMvc.perform(
                post("/new/checking-account/" + accountHolderRepository.findAll().get(0).getId())
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

//        Make sure that the newly created account has been linked to Cayetano
        assertTrue(result.getResponse().getContentAsString().contains("Cayetano"));
        System.out.println(studentAccountRepository.findAll().size());
//        Make sure that it has been also linked to the secondary owner
        assertEquals(accountRepository.findAll().get(0).getId(),
                accountHolderRepository.findAll().get(1).getSecondaryAccounts().get(0).getId());
//        Make sure that this new account has been stored in the repository
        assertEquals(2, accountRepository.findAll().size());
    }

    @Test
    void createCheckAccount_invalidValues_Exception() throws Exception {
        BigDecimal balance = new BigDecimal("1000");
        long secondaryOwnerId = accountHolderRepository.findAll().get(1).getId();
        CheckingAcDTO checkingAcDTO = transformer.assembleCheckingAcDTO(balance, "uno", secondaryOwnerId);

        String body = objectMapper.writeValueAsString(checkingAcDTO);
        System.out.println(body);
        MvcResult result = mockMvc.perform(
                post("/new/checking-account/" + accountHolderRepository.findAll().get(0).getId())
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

//        Make sure that the newly created account has been linked to Cayetano
        assertTrue(result.getResolvedException().toString().contains("The secret key must have at least 7 digits."));
//        We have a test account, this should be 1
        assertEquals(1, accountRepository.findAll().size());
    }
}