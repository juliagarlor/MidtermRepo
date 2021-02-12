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
class CheckingAccountControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private CheckingAccountRepository checkingAccountRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AccountHolderService accountHolderService;

    private Transformer transformer = new Transformer();

    @BeforeEach
    void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();

        NameDTO name = transformer.assembleNameDTO("Rodriguez", "Cayetano", "Jesús", Salutation.Mr);
        AddressDTO primaryAddress = transformer.assembleAddressDTO(1, "Castilla", "Madrid", "España");
        AccountHolderDTO primaryOwner = transformer.assembleAccountHolderDTO(name,25, "contraseña",
                LocalDateTime.of(1996, 5, 20, 18, 40, 00), primaryAddress, null);
        accountHolderService.createAccountHolder(primaryOwner);

        NameDTO name2 = transformer.assembleNameDTO("Rodriguez", "María", "Jesús", Salutation.Mrs);
        AddressDTO primaryAddress2 = transformer.assembleAddressDTO(1, "Castilla", "Madrid", "España");
        AccountHolderDTO secondaryOwner = transformer.assembleAccountHolderDTO(name2, 40, "contraseña2",
                LocalDateTime.of(1980, 5, 20, 18, 40, 00), primaryAddress2, null);
        accountHolderService.createAccountHolder(secondaryOwner);

        BigDecimal balance = new BigDecimal("1000");
        long secondaryOwnerId = accountHolderRepository.findAll().get(1).getId();
        BigDecimal monthlyMaintenanceFee = new BigDecimal("6");
        CheckingAcDTO checkingAcDTO = transformer.assembleCheckingAcDTO(balance, "mocatriz", secondaryOwnerId);

//        Creating an admin
        AdminDTO adminDTO = transformer.assembleAdminDTO(
                transformer.assembleNameDTO("Sanchez", "Victoria", null, Salutation.Ms), 20,
                "contraseña3");
        String body = objectMapper.writeValueAsString(adminDTO);
        mockMvc.perform(post("/register/administrator").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        CustomUserDetails user = new CustomUserDetails(adminRepository.findAll().get(0));
//        Cayetano of the future will open an checking account
        String body2 = objectMapper.writeValueAsString(checkingAcDTO);
        mockMvc.perform(
                post("/new/checking-account/" + accountHolderRepository.findAll().get(0).getId()).with(user(user))
                        .content(body2).contentType(MediaType.APPLICATION_JSON));
    }

    @AfterEach
    void tearDown() {
        checkingAccountRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    void checkAccount_validLogin_Balance() throws Exception {
        CustomUserDetails user = new CustomUserDetails(accountHolderRepository.findAll().get(0));

        MvcResult result = mockMvc.perform(
                get("/check/checking-account/" + checkingAccountRepository.findAll().get(0).getId()).with(user(user)))
                .andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("1000"));
    }

    @Test
    void checkAccount_invalidLogin_Exception() throws Exception {
//        Changing secondaryOwner to null
        CheckingAccount test = checkingAccountRepository.findAll().get(0);
        test.setSecondaryOwner(null);
        checkingAccountRepository.save(test);

        CustomUserDetails user = new CustomUserDetails(accountHolderRepository.findAll().get(1));

        MvcResult result = mockMvc.perform(
                get("/check/checking-account/" + test.getId()).with(user(user)))
                .andExpect(status().isUnauthorized()).andReturn();

        assertTrue(result.getResolvedException().toString().contains("You are not authorized to see this data"));
    }

    @Test
    void addAmount_validValues_CheckingAccount() throws Exception {
        Money amount = new Money(new BigDecimal("20"));
        CheckingAccount test = checkingAccountRepository.findAll().get(0);

        CustomUserDetails user = new CustomUserDetails(adminRepository.findAll().get(0));

        String body = objectMapper.writeValueAsString(amount);
        System.out.println(body);
        MvcResult result = mockMvc.perform(
                patch("/admin/checking-account/" + test.getId() + "/increaseBalance").with(user(user))
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(new BigDecimal("1020.00"), checkingAccountRepository.findAll().get(0).getBalance().getAmount());
    }

    @Test
    void subtractAmount_amountSmallerThanBalance_Account() throws Exception {
        Money amount = new Money(new BigDecimal("20"));
        CheckingAccount test = checkingAccountRepository.findAll().get(0);

        CustomUserDetails user = new CustomUserDetails(adminRepository.findAll().get(0));

        String body = objectMapper.writeValueAsString(amount);
        System.out.println(body);
        MvcResult result = mockMvc.perform(
                patch("/admin/checking-account/" + test.getId() + "/decreaseBalance").with(user(user))
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        assertEquals(new BigDecimal("980.00"), checkingAccountRepository.findAll().get(0).getBalance().getAmount());

    }

    @Test
    void subtractAmount_amountBiggerThanBalance_Account() throws Exception {
        Money amount = new Money(new BigDecimal("1020"));
        CheckingAccount test = checkingAccountRepository.findAll().get(0);

        CustomUserDetails user = new CustomUserDetails(adminRepository.findAll().get(0));

        String body = objectMapper.writeValueAsString(amount);
        System.out.println(body);
        MvcResult result = mockMvc.perform(
                patch("/admin/checking-account/" + test.getId() + "/decreaseBalance").with(user(user))
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable())
                .andReturn();

        assertTrue(result.getResolvedException().toString().contains("As broke as Donald Trump"));
        assertEquals(new BigDecimal("1000.00"), checkingAccountRepository.findAll().get(0).getBalance().getAmount());

    }
}