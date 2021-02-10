package com.ironhack.theBestMidtermProject.controller.impl;

import com.fasterxml.jackson.databind.*;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    private Ensambler ensambler = new Ensambler();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        NameDTO name = ensambler.ensambleNameDTO("Rodriguez", "Cayetano", "Jesús", Salutation.Mr);
        AddressDTO primaryAddress = ensambler.ensambleAddressDTO(1, "Castilla", "Madrid", "España");
        AccountHolderDTO primaryOwner = ensambler.ensambleAccountHolderDTO(name,25, "contraseña",
                LocalDateTime.of(2002, 5, 20, 18, 40, 00), primaryAddress, null);
        accountHolderService.createAccountHolder(primaryOwner);

        NameDTO name2 = ensambler.ensambleNameDTO("Rodriguez", "María", "Jesús", Salutation.Mrs);
        AddressDTO primaryAddress2 = ensambler.ensambleAddressDTO(1, "Castilla", "Madrid", "España");
        AccountHolderDTO secondaryOwner = ensambler.ensambleAccountHolderDTO(name2, 40, "contraseña2",
                LocalDateTime.of(1980, 5, 20, 18, 40, 00), primaryAddress2, null);
        accountHolderService.createAccountHolder(secondaryOwner);
    }

    @AfterEach
    void tearDown() {
        checkingAccountRepository.deleteAll();
        studentAccountRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    void createCheckAccount_validValues_CheckingAccount() throws Exception {
        assertEquals(0, checkingAccountRepository.findAll().size());

        BigDecimal balance = new BigDecimal("1000");
        long secondaryOwnerId = accountHolderRepository.findAll().get(1).getId();
        CheckingAcDTO checkingAcDTO = ensambler.ensambleCheckingAcDTO(balance, "menudoPelazo", secondaryOwnerId);

        String body = objectMapper.writeValueAsString(checkingAcDTO);
        System.out.println(body);
        MvcResult result = mockMvc.perform(
                post("/new/checking-account/" + accountHolderRepository.findAll().get(0).getId())
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

//        Make sure that the newly created account has been linked to Cayetano
        assertTrue(result.getResponse().getContentAsString().contains("Cayetano"));
//        assertTrue(accountRepository.findAll().get(0).toString().contains("menudoPelazo"));
//        Make sure that it has been also linked to the secondary owner
        assertEquals(accountRepository.findAll().get(0).getId(),
                accountHolderRepository.findAll().get(1).getSecondaryAccounts().get(0).getId());
//        Make sure that this new account has been stored in the repository
        assertEquals(1, accountRepository.findAll().size());

//        TODO: MATAR A ARNOLDO
    }

    @Test
    void createCheckAccount_validValues_StudentAccount() throws Exception {
        assertEquals(0, checkingAccountRepository.findAll().size());

//        Reset the age of Cayetano to be young, as he would wish
        AccountHolder primaryOwner = accountHolderRepository.findAll().get(0);
        primaryOwner.setAge(18);
        accountHolderRepository.save(primaryOwner);

        BigDecimal balance = new BigDecimal("1000");
        long secondaryOwnerId = accountHolderRepository.findAll().get(1).getId();
        CheckingAcDTO checkingAcDTO = ensambler.ensambleCheckingAcDTO(balance, "menudoPelazo", secondaryOwnerId);

        String body = objectMapper.writeValueAsString(checkingAcDTO);
        System.out.println(body);
        MvcResult result = mockMvc.perform(
                post("/new/checking-account/" + accountHolderRepository.findAll().get(0).getId())
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

//        Make sure that the newly created account has been linked to Cayetano
        assertTrue(result.getResponse().getContentAsString().contains("Cayetano"));
//        assertTrue(accountRepository.findAll().get(0).toString().contains("menudoPelazo"));
//        Make sure that it has been also linked to the secondary owner
        assertEquals(accountRepository.findAll().get(0).getId(),
                accountHolderRepository.findAll().get(1).getSecondaryAccounts().get(0).getId());
//        Make sure that this new account has been stored in the repository
        assertEquals(1, checkingAccountRepository.findAll().size());

//        TODO: MATAR A ARNOLDO
    }

    @Test
    void createCheckAccount_invalidValues_Exception() throws Exception {
        BigDecimal balance = new BigDecimal("1000");
        long secondaryOwnerId = accountHolderRepository.findAll().get(1).getId();
        CheckingAcDTO checkingAcDTO = ensambler.ensambleCheckingAcDTO(balance, "uno", secondaryOwnerId);

        String body = objectMapper.writeValueAsString(checkingAcDTO);
        System.out.println(body);
        MvcResult result = mockMvc.perform(
                post("/new/checking-account/" + accountHolderRepository.findAll().get(0).getId())
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

//        Make sure that the newly created account has been linked to Cayetano
        assertTrue(result.getResolvedException().toString().contains("The secret key must have at least 7 digits."));
        assertEquals(0, accountRepository.findAll().size());

//        TODO: MATAR A ARNOLDO
    }

    @Test
    void addAmount() {
    }

    @Test
    void subtractAmount() {
    }
}