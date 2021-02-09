package com.ironhack.theBestMidtermProject.controller.impl;

import com.fasterxml.jackson.databind.*;
import com.ironhack.theBestMidtermProject.repository.users.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;
import com.ironhack.theBestMidtermProject.utils.enums.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;
import org.springframework.http.*;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.*;
import org.springframework.web.context.*;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AdminControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void tearDown() {
        adminRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    void createAdmin_validValues_Admin() throws Exception {
        assertEquals(0, adminRepository.findAll().size());
        NameDTO nameDTO = new NameDTO("Lopez", "Cris", Salutation.Ms);
        String password = "ensaladilla";
        AdminDTO adminDTO = new AdminDTO(nameDTO, 20, password);

        String body = objectMapper.writeValueAsString(adminDTO);
        System.out.println(body);
        MvcResult result = mockMvc.perform(
                post("/register/administrator")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("Cris"));
        assertEquals(1, roleRepository.findAll().size());
        assertEquals(1, adminRepository.findAll().size());
    }

    @Test
    void createAdmin_invalidValues_Admin() throws Exception {
        assertEquals(0, adminRepository.findAll().size());
        NameDTO nameDTO = new NameDTO("Lopez", "Cris", Salutation.Ms);
        String password = "ensaladilla";
        AdminDTO adminDTO = new AdminDTO(nameDTO, 17, password);

        String body = objectMapper.writeValueAsString(adminDTO);
        MvcResult result = mockMvc.perform(
                post("/register/administrator")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

        assertTrue(result.getResolvedException().getMessage().contains("of age."));

        nameDTO.setFirstName("");
        adminDTO.setNameDTO(nameDTO);
        adminDTO.setAge(20);
        body = objectMapper.writeValueAsString(adminDTO);
        result = mockMvc.perform(
                post("/register/administrator")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
        assertTrue(result.getResolvedException().getMessage().contains("introduce the first name"));

        assertEquals(0, roleRepository.findAll().size());
        assertEquals(0, adminRepository.findAll().size());
    }
}