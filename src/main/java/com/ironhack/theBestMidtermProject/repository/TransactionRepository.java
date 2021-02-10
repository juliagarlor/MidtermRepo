package com.ironhack.theBestMidtermProject.repository;

import com.ironhack.theBestMidtermProject.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.*;
import org.springframework.stereotype.*;

import java.math.*;
import java.util.*;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, Long> {
//    Transferences sent today:
    @Query(value = "SELECT SUM(amount) FROM transference WHERE emisor = :id AND moment >= NOW() - INTERVAL 1 DAY", nativeQuery = true)
    BigDecimal transactionsToday(@Param("id") long emisorId);

//    Transferences each previous day:
    @Query(value = "SELECT SUM(amount), DAY(transference_date) FROM transference WHERE origin_account = :id GROUP BY DAY(transference_date)", nativeQuery = true)
    List<BigDecimal> transactionsPerDay(@Param("id") long emisorId);

//    Transferences in the last second:
    @Query(value = "SELECT * FROM transference WHERE origin_account = :id AND transference_date >= NOW() - INTERVAL 3601 SECOND", nativeQuery = true)
    List<BigDecimal> transactionsInLastSecond(@Param("id") long emisorId);
}
