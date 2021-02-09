package com.ironhack.theBestMidtermProject.controller.impl;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;
import com.ironhack.theBestMidtermProject.model.users.*;
import com.ironhack.theBestMidtermProject.repository.users.*;
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

import javax.validation.*;
import javax.validation.constraints.*;
import java.math.*;
import java.time.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void tearDown() {
        accountHolderRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    void checkBalance() {
    }

    @Test
    void createAccountHolder_validDTO_AccountHolder() throws Exception {
        assertEquals(0, accountHolderRepository.findAll().size());
        NameDTO nameDTO = new NameDTO("Lopez", "Cris", Salutation.Ms);
        String password = "password";
        AddressDTO primaryAddressDTO = new AddressDTO(2, "Los Pinos", "Tarancón", "España");
        AddressDTO mailingAddressDTO = new AddressDTO(5, "Las Olivas", "Tarancón", "España");
        AccountHolderDTO accountHolderDTO = new AccountHolderDTO(nameDTO, 20, password, LocalDateTime.of(1996, 12, 19, 17, 0, 0),
                primaryAddressDTO, mailingAddressDTO);

        String body = objectMapper.writeValueAsString(accountHolderDTO);
        System.out.println(body);
        MvcResult result = mockMvc.perform(
                post("/register/accountHolder")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("Cris"));
        assertEquals(1, roleRepository.findAll().size());
        assertEquals(1, accountHolderRepository.findAll().size());
    }

    @Test
    void createAccountHolder_invalidDTO_Exception() throws Exception {
//        lastName to null
        assertEquals(0, accountHolderRepository.findAll().size());
        NameDTO nameDTO = new NameDTO(null, "Cris", Salutation.Ms);
        String password = "password";
        AddressDTO primaryAddressDTO = new AddressDTO(1, "Los Pinos", "Tarancón", "España");
        AddressDTO mailingAddressDTO = new AddressDTO(5, "Las Olivas", "Tarancón", "España");
        AccountHolderDTO accountHolderDTO = new AccountHolderDTO(nameDTO, 20, password, LocalDateTime.of(1996, 12, 19, 17, 0, 0),
                primaryAddressDTO, mailingAddressDTO);

        String body = objectMapper.writeValueAsString(accountHolderDTO);
        System.out.println(body);
        MvcResult result = mockMvc.perform(
                post("/register/accountHolder")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
        assertEquals(0, accountHolderRepository.findAll().size());
    }
}