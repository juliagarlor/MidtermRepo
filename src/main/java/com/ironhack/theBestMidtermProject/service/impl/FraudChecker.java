package com.ironhack.theBestMidtermProject.service.impl;

import com.ironhack.theBestMidtermProject.model.*;
import com.ironhack.theBestMidtermProject.repository.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.math.*;
import java.util.*;

@Service
public class FraudChecker {

    @Autowired
    private TransactionRepository transactionRepository;

    public boolean checkFraudInADay(Transactions transactions){
        long emisorId = transactions.getEmisorId().getId();

//        Sum of transactions today (since I have not save this one in the repository yet, I need to add it here)
        BigDecimal transactionsToday = transactionRepository.transactionsToday(emisorId).add(transactions.getAmount().getAmount());

//        Searching for maximum in previous days
        List<BigDecimal> previousTransactions = transactionRepository.transactionsPerDay(emisorId);

        BigDecimal max = new BigDecimal("0");
        for (BigDecimal b : previousTransactions) {
            max = (max.compareTo(b) < 0) ? b : max;
        }

//        Looking for fraud as if we were in Hardcore Pawn

//        If this is the first day of the account or if today's total transactions is smaller than 150% of the maximum
//        of the previous day, we declare NOT FRAUDULENT:
        if (previousTransactions.size() <= 1 || transactionsToday.compareTo(max.multiply(new BigDecimal("1.50"))) < 0){
            return false;
        }
//        otherwise, I am sorry, but you should run
        else {
            return true;
        }
    }

    public boolean checkFraudInLastSecond(Transactions transactions){

        long emisorId = transactions.getEmisorId().getId();
//        Searching for a list of transactions in the last second, good luck
        List<BigDecimal> transactionsOfLastSecond = transactionRepository.transactionsInLastSecond(emisorId);

//        if there is more than two operations in the last second, we will consider it fraud, but since this last
//        transaction has not been saved in the repository yet, we should add it to the list size of the last transactions.
//        So initially, the comparison should be: transactionsOfLastSecond.size() > 2, but the right part is actually
//        transactionsOfLastSecond.size() + 1, so:

        if (transactionsOfLastSecond.size() > 1){
//            Wow, are you Flash? Because your money is running away at the speed of light
            return true;
        }else {
            return false;
        }
    }
}
