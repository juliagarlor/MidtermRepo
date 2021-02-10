package com.ironhack.theBestMidtermProject.controller.impl;

import com.fasterxml.jackson.databind.*;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ThirdPartyControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    @Autowired
    private RoleRepository roleRepository;

    private Transformer transformer = new Transformer();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void tearDown() {
        thirdPartyRepository.deleteAll();
        roleRepository.deleteAll();
    }

    @Test
    void createThirdParty_validValues_ThirdParty() throws Exception {
        assertEquals(0, thirdPartyRepository.findAll().size());
        NameDTO nameDTO = transformer.ensambleNameDTO("Lopez", "Cris", null, Salutation.Ms);
        ThirdPartyDTO thirdPartyDTO = transformer.ensambleThirdPartyDTO(nameDTO, 20, "rusaConKetchup", "ensaladilla");

        String body = objectMapper.writeValueAsString(thirdPartyDTO);
        System.out.println(body);
        MvcResult result = mockMvc.perform(
                post("/register/third-party")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("Cris"));
        assertEquals(1, roleRepository.findAll().size());
        assertEquals(1, thirdPartyRepository.findAll().size());
    }

    @Test
    void createThirdParty_invalidValues_ThirdParty() throws Exception {
        assertEquals(0, thirdPartyRepository.findAll().size());
        NameDTO nameDTO = transformer.ensambleNameDTO("Lopez", "Cris", null, Salutation.Ms);
        ThirdPartyDTO thirdPartyDTO = transformer.ensambleThirdPartyDTO(nameDTO, 17, "rusaConKetchup", "ensaladilla");

        String body = objectMapper.writeValueAsString(thirdPartyDTO);
        MvcResult result = mockMvc.perform(
                post("/register/third-party")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();

        assertTrue(result.getResolvedException().getMessage().contains("of age."));

        nameDTO.setFirstName("");
        thirdPartyDTO.setNameDTO(nameDTO);
        thirdPartyDTO.setAge(20);
        body = objectMapper.writeValueAsString(thirdPartyDTO);
        result = mockMvc.perform(
                post("/register/third-party")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andReturn();
        assertTrue(result.getResolvedException().getMessage().contains("introduce the first name"));

        assertEquals(0, roleRepository.findAll().size());
        assertEquals(0, thirdPartyRepository.findAll().size());
    }
}