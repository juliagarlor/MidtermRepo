package com.ironhack.theBestMidtermProject.service.impl;

import com.ironhack.theBestMidtermProject.model.*;
import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.repository.*;
import com.ironhack.theBestMidtermProject.repository.accounts.*;
import com.ironhack.theBestMidtermProject.repository.users.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import com.ironhack.theBestMidtermProject.utils.dtos.*;
import com.ironhack.theBestMidtermProject.utils.enums.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.context.*;

import java.math.*;
import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FraudCheckerTest {

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private CheckingAccountRepository checkingAccountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountHolderService accountHolderService;

    @Autowired
    private FraudChecker fraudChecker;

    private Transformer transformer = new Transformer();

    @BeforeEach
    void setUp() {
//        Creating a primary owner
        NameDTO name = transformer.assembleNameDTO("Rodriguez", "Cayetano", "Jesús", Salutation.Mr);
        AddressDTO primaryAddress = transformer.assembleAddressDTO(1, "Castilla", "Madrid", "España");
        AccountHolderDTO primaryOwner = transformer.assembleAccountHolderDTO(name,18, "contraseña",
                LocalDateTime.of(2002, 5, 20, 18, 40, 00), primaryAddress, null);
        accountHolderService.createAccountHolder(primaryOwner);

//        Creating a checkingAccount
        CheckingAccount checkingAccount = new CheckingAccount(new Money(new BigDecimal("1000")), Status.ACTIVE,
                accountHolderRepository.findAll().get(0), null, "laEscuelaDeCalor");
        checkingAccountRepository.save(checkingAccount);

//        And a transaction with a third party
        Transactions transactions = new Transactions(checkingAccount, null, new Money(new BigDecimal("100")),
                LocalDateTime.of(2021, 2, 14, 0, 0, 0));
        transactionRepository.save(transactions);
    }

    @AfterEach
    void tearDown() {
        transactionRepository.deleteAll();
        checkingAccountRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    void checkFraudInADay_validTransactions_false() {
//        A new transaction of the same amount, the next day
        Transactions test = new Transactions(checkingAccountRepository.findAll().get(0), null,
                new Money(new BigDecimal("100")),
                LocalDateTime.of(2021, 2, 15, 0, 0, 0));
        assertFalse(fraudChecker.checkFraudInADay(test));
    }

    @Test
    void checkFraudInADay_fraudTransactions_true() {
//        A new transaction of an amount 160% bigger, next day, one minute later
        Transactions test = new Transactions(checkingAccountRepository.findAll().get(0), null,
                new Money(new BigDecimal("300")),
                LocalDateTime.of(2021, 2, 15, 0, 1, 0));
        assertTrue(fraudChecker.checkFraudInADay(test));
    }

    @Test
    void checkFraudInLastSecond_moreThanTwoTransactionsInASec_true() {
        Transactions test = new Transactions(checkingAccountRepository.findAll().get(0), null,
                new Money(new BigDecimal("100")),
                LocalDateTime.now());

        Transactions test1 = new Transactions(checkingAccountRepository.findAll().get(0), null,
                new Money(new BigDecimal("100")),
                LocalDateTime.now());
        transactionRepository.saveAll(List.of(test, test1));

        Transactions test2 = new Transactions(checkingAccountRepository.findAll().get(0), null,
                new Money(new BigDecimal("100")),
                LocalDateTime.now());

        assertTrue(fraudChecker.checkFraudInADay(test2));
    }
}