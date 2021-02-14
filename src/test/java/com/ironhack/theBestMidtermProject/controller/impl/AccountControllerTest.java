package com.ironhack.theBestMidtermProject.controller.impl;

import com.fasterxml.jackson.databind.*;
import com.ironhack.theBestMidtermProject.model.*;
import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.model.users.*;
import com.ironhack.theBestMidtermProject.repository.*;
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

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ThirdPartyController thirdPartyController;

    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    @Autowired
    private AdminRepository adminRepository;

    private Transformer transformer = new Transformer();

    @BeforeEach
    void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();

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

//        An admin
        AdminDTO adminDTO = transformer.assembleAdminDTO(
                transformer.assembleNameDTO("Sanchez", "Victoria", null, Salutation.Ms), 20,
                "contraseña3");
        String body = objectMapper.writeValueAsString(adminDTO);
        mockMvc.perform(post("/register/administrator").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

//        And a test checkingAccount for the add and subtractBalance method
        Money balance = new Money(new BigDecimal("1000"));
        CheckingAccount checkingAccount = new CheckingAccount(balance, Status.ACTIVE, accountHolderRepository.findAll().get(0),
                null, "comoLoPasamos");
        checkingAccountRepository.save(checkingAccount);

//        Another one
        Money balance2 = new Money(new BigDecimal("1500"));
        CheckingAccount checkingAccount2 = new CheckingAccount(balance, Status.ACTIVE, accountHolderRepository.findAll().get(1),
                null, "comoLoPasamos");
        checkingAccountRepository.save(checkingAccount2);

//        And a transaction in the repository just in case
        Transactions transactions = new Transactions(checkingAccount2, checkingAccount,
                new Money(new BigDecimal("50")), LocalDateTime.now());

        transactionRepository.save(transactions);

//        And a third party for the third party tests
        ThirdPartyDTO test = transformer.assembleThirdPartyDTO(
                transformer.assembleNameDTO("Perez", "Pablo", null, Salutation.Mr),
                40, "hashKey", "laFincaEnSegovia");
        thirdPartyController.createThirdParty(test);
    }

    @AfterEach
    void tearDown() {
        transactionRepository.deleteAll();
        accountRepository.deleteAll();
        checkingAccountRepository.deleteAll();
        studentAccountRepository.deleteAll();
        accountHolderRepository.deleteAll();
        thirdPartyRepository.deleteAll();
        adminRepository.deleteAll();
    }

    @Test
    void createCheckAccount_validValues_CheckingAccount() throws Exception {
        assertEquals(2, checkingAccountRepository.findAll().size());

        CustomUserDetails user = new CustomUserDetails(adminRepository.findAll().get(0));

        BigDecimal balance = new BigDecimal("1000");
        Long secondaryOwnerId = accountHolderRepository.findAll().get(1).getId();
        CheckingAcDTO checkingAcDTO = transformer.assembleCheckingAcDTO(balance, "menudoPelazo", secondaryOwnerId);

        String body = objectMapper.writeValueAsString(checkingAcDTO);
        System.out.println(body);
        MvcResult result = mockMvc.perform(
                post("/new/checking-account/" + accountHolderRepository.findAll().get(0).getId()).with(user(user))
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

//        Make sure that the newly created account has been linked to Cayetano
        assertTrue(result.getResponse().getContentAsString().contains("Cayetano"));
        assertEquals(3, accountRepository.findAll().size());
    }

    @Test
    void createCheckAccount_validValues_StudentAccount() throws Exception {
        assertEquals(2, checkingAccountRepository.findAll().size());

        CustomUserDetails user = new CustomUserDetails(adminRepository.findAll().get(0));
//        Reset the age of Cayetano to be young, as he would wish
        AccountHolder primaryOwner = accountHolderRepository.findAll().get(0);
        primaryOwner.setAge(18);
        accountHolderRepository.save(primaryOwner);

        BigDecimal balance = new BigDecimal("1000");
        Long secondaryOwnerId = accountHolderRepository.findAll().get(1).getId();
        CheckingAcDTO checkingAcDTO = transformer.assembleCheckingAcDTO(balance, "menudoPelazo", secondaryOwnerId);

        String body = objectMapper.writeValueAsString(checkingAcDTO);
        System.out.println(body);
        MvcResult result = mockMvc.perform(
                post("/new/checking-account/" + primaryOwner.getId()).with(user(user))
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

//        Make sure that the newly created account has been linked to Cayetano
        assertTrue(result.getResponse().getContentAsString().contains("Cayetano"));
        assertEquals(3, accountRepository.findAll().size());
    }

    @Test
    void createCheckAccount_invalidValues_Exception() throws Exception {

        CustomUserDetails user = new CustomUserDetails(adminRepository.findAll().get(0));

        BigDecimal balance = new BigDecimal("1000");
        Long secondaryOwnerId = accountHolderRepository.findAll().get(1).getId();
        CheckingAcDTO checkingAcDTO = transformer.assembleCheckingAcDTO(balance, "uno", secondaryOwnerId);

        String body = objectMapper.writeValueAsString(checkingAcDTO);
        MvcResult result = mockMvc.perform(
                post("/new/checking-account/" + accountHolderRepository.findAll().get(0).getId()).with(user(user))
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

//        Make sure that the newly created account has been linked to Cayetano
        assertTrue(result.getResolvedException().toString().contains("The secret key must have at least 7 digits."));
//        We have a test account, this should be 1
        assertEquals(2, accountRepository.findAll().size());
    }

    @Test
    void transfer_validValues_Transactions() throws Exception {

        CustomUserDetails user = new CustomUserDetails(accountHolderRepository.findAll().get(1));

        Long emisorId = checkingAccountRepository.findAll().get(1).getId();
        Long receptorId = checkingAccountRepository.findAll().get(0).getId();
        BigDecimal amount = new BigDecimal("50");
        LocalDateTime moment = LocalDateTime.now();

        TransactionsDTO transactionsDTO = transformer.assembleTransactionsDTO(emisorId, receptorId, amount, moment);
        String body = objectMapper.writeValueAsString(transactionsDTO);

        MvcResult result = mockMvc.perform(
                post("/transfer").with(user(user)).content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("50"));
    }

    @Test
    void transfer_fraud_Exception() throws Exception {
//        Adding a second transaction for this moment, so the next one jumps because of 3 transactions in less than 1 second
        Transactions transactions = new Transactions(checkingAccountRepository.findAll().get(1), checkingAccountRepository.findAll().get(0),
                new Money(new BigDecimal("50")), LocalDateTime.now());

        transactionRepository.save(transactions);

//        Jump from here
        CustomUserDetails user = new CustomUserDetails(accountHolderRepository.findAll().get(1));

        Long emisorId = checkingAccountRepository.findAll().get(1).getId();
        Long receptorId = checkingAccountRepository.findAll().get(0).getId();
        BigDecimal amount = new BigDecimal("50");
        LocalDateTime moment = LocalDateTime.now();

        TransactionsDTO transactionsDTO = transformer.assembleTransactionsDTO(emisorId, receptorId, amount, moment);
        String body = objectMapper.writeValueAsString(transactionsDTO);

        MvcResult result = mockMvc.perform(
                post("/transfer").with(user(user)).content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable())
                .andReturn();

        assertTrue(result.getResolvedException().toString().contains("You may need money for a ticket,"));
    }

    @Test
    void transfer_biggerAmountThanBalance_Exception() throws Exception {

        CustomUserDetails user = new CustomUserDetails(accountHolderRepository.findAll().get(1));

        Long emisorId = checkingAccountRepository.findAll().get(1).getId();
        Long receptorId = checkingAccountRepository.findAll().get(0).getId();
        BigDecimal amount = new BigDecimal("2000");
        LocalDateTime moment = LocalDateTime.now();

        TransactionsDTO transactionsDTO = transformer.assembleTransactionsDTO(emisorId, receptorId, amount, moment);
        String body = objectMapper.writeValueAsString(transactionsDTO);

        MvcResult result = mockMvc.perform(
                post("/transfer").with(user(user)).content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotAcceptable())
                .andReturn();

        assertTrue(result.getResolvedException().toString().contains("The emisor account does not have enough funds"));
    }

    @Test
    void transferWithThirdParty_thirdPartyEmisor_transference() throws Exception {
//        Logging with the third party
        CustomUserDetails user = new CustomUserDetails(thirdPartyRepository.findAll().get(0));

//        Emisor account id is null
        Long receptorId = checkingAccountRepository.findAll().get(0).getId();
        BigDecimal amount = new BigDecimal("50");
        LocalDateTime moment = LocalDateTime.now();

        TransactionsDTO transactionsDTO = transformer.assembleTransactionsDTO(null, receptorId, amount, moment);
        String body = objectMapper.writeValueAsString(transactionsDTO);

        MvcResult result = mockMvc.perform(
                post("/transfer/hashKey/comoLoPasamos").with(user(user)).content(body).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated())
                .andReturn();

//        Checking if the response is Ok
        assertTrue(result.getResponse().getContentAsString().contains("50"));
//        Checking if the transference has been applied
        assertEquals(0, checkingAccountRepository.findById(receptorId).get().getBalance().getAmount()
                .compareTo(new BigDecimal("1050.0000")));
    }

    @Test
    void transferWithThirdParty_thirdPartyReceptor_transference() throws Exception {
//        Logging with the third party
        CustomUserDetails user = new CustomUserDetails(thirdPartyRepository.findAll().get(0));

//        Emisor account id is null
        Long emisorId = checkingAccountRepository.findAll().get(0).getId();
        BigDecimal amount = new BigDecimal("50");
        LocalDateTime moment = LocalDateTime.now();

        TransactionsDTO transactionsDTO = transformer.assembleTransactionsDTO(emisorId, null, amount, moment);
        String body = objectMapper.writeValueAsString(transactionsDTO);

        MvcResult result = mockMvc.perform(
                post("/transfer/hashKey/comoLoPasamos").with(user(user)).content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

//        Checking if the response is Ok
        assertTrue(result.getResponse().getContentAsString().contains("50"));
//        Checking if the transference has been applied
        assertEquals(0, checkingAccountRepository.findById(emisorId).get().getBalance().getAmount()
                .compareTo(new BigDecimal("950.0000")));
    }
}