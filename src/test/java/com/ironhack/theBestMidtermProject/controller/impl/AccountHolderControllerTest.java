package com.ironhack.theBestMidtermProject.controller.impl;

import com.fasterxml.jackson.core.*;
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
import org.springframework.boot.autoconfigure.security.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.authority.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.preauth.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.*;
import org.springframework.web.context.*;

import javax.validation.*;
import javax.validation.constraints.*;
import java.math.*;
import java.security.*;
import java.time.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@SpringBootTest
class AccountHolderControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AccountHolderService accountHolderService;

    @Autowired
    private SavingsAccountRepository savingsAccountRepository;

    private Ensambler ensambler = new Ensambler();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

//        Create an accountHolder
        NameDTO name = ensambler.ensambleNameDTO("Rosalía", "La", null, Salutation.Ms);
        AddressDTO primaryAddress = ensambler.ensambleAddressDTO(1, "Castilla", "Madrid", "España");
        AccountHolderDTO primaryOwner = ensambler.ensambleAccountHolderDTO(name,25, "contraseña",
                LocalDateTime.of(2002, 5, 20, 18, 40, 00), primaryAddress, null);
        accountHolderService.createAccountHolder(primaryOwner);

//        And a test savingsAccount for the checkBalance method
        Money balance = new Money(new BigDecimal("1000"));
        Money minimumBalance = new Money(new BigDecimal("100"));
        BigDecimal interestRate = new BigDecimal("0.4");
        SavingsAccount savingsAccount = new SavingsAccount(balance, accountHolderRepository.findAll().get(0),
                null, "comoLoPasamos", Status.ACTIVE, minimumBalance, interestRate);
        savingsAccountRepository.save(savingsAccount);
    }

    @AfterEach
    void tearDown() {
        accountHolderRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    void checkBalance_validAccountHolder_Balance() throws Exception {
//        String testId = String.valueOf(accountHolderRepository.findAll().get(0).getId());
//        org.springframework.security.core.userdetails.User user = new User(testId, "contraseña",
//                AuthorityUtils.createAuthorityList("ACCOUNT_HOLDER"));
//        TestingAuthenticationToken testingAuthenticationToken = new TestingAuthenticationToken(user,null);
//
//        MvcResult result = mockMvc.perform(
//                get("/balance/" + savingsAccountRepository.findAll().get(0).getId()).with(user(user)))
//                .andExpect(status().isOk()).andReturn();
//
//        assertTrue(result.getResponse().getContentAsString().contains("1000"));
////        assertEquals(1, roleRepository.findAll().size());
////        assertEquals(1, accountHolderRepository.findAll().size());
    }

    @Test
    void createAccountHolder_validDTO_AccountHolder() throws Exception {
        assertEquals(1, accountHolderRepository.findAll().size());
        NameDTO nameDTO = ensambler.ensambleNameDTO("Lopez", "Cris", null, Salutation.Ms);
        String password = "password";
        AddressDTO primaryAddressDTO = ensambler.ensambleAddressDTO(2, "Los Pinos", "Tarancón", "España");
        AddressDTO mailingAddressDTO = ensambler.ensambleAddressDTO(5, "Las Olivas", "Tarancón", "España");
        AccountHolderDTO accountHolderDTO = ensambler.ensambleAccountHolderDTO(nameDTO, 20, password,
                LocalDateTime.of(1996, 12, 19, 17, 0, 0),
                primaryAddressDTO, mailingAddressDTO);

        String body = objectMapper.writeValueAsString(accountHolderDTO);
        System.out.println(body);
        MvcResult result = mockMvc.perform(
                post("/register/accountHolder")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("Cris"));
        assertEquals(2, roleRepository.findAll().size());
        assertEquals(2, accountHolderRepository.findAll().size());
    }

    @Test
    void createAccountHolder_invalidDTO_Exception() throws Exception {
//        lastName to null
        assertEquals(1, accountHolderRepository.findAll().size());
        NameDTO nameDTO = ensambler.ensambleNameDTO(null, "Cris", null, Salutation.Ms);
        String password = "password";
        AddressDTO primaryAddressDTO = ensambler.ensambleAddressDTO(1, "Los Pinos", "Tarancón", "España");
        AddressDTO mailingAddressDTO = ensambler.ensambleAddressDTO(5, "Las Olivas", "Tarancón", "España");
        AccountHolderDTO accountHolderDTO = ensambler.ensambleAccountHolderDTO(nameDTO, 20, password,
                LocalDateTime.of(1996, 12, 19, 17, 0, 0),
                primaryAddressDTO, mailingAddressDTO);

        String body = objectMapper.writeValueAsString(accountHolderDTO);
        System.out.println(body);
        MvcResult result = mockMvc.perform(
                post("/register/accountHolder")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
        assertEquals(1, accountHolderRepository.findAll().size());
    }
}