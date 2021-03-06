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
    @Query(value = "SELECT SUM(amount) FROM transactions WHERE emisor_id = :id AND moment >= NOW() - INTERVAL 1 DAY", nativeQuery = true)
    public BigDecimal transactionsToday(@Param("id") Long emisorId);

//    Transferences each previous day:
    @Query(value = "SELECT SUM(amount), DAY(moment) FROM transactions WHERE emisor_id = :id GROUP BY DAY(moment)", nativeQuery = true)
    List<BigDecimal> transactionsPerDay(@Param("id") Long emisorId);

//    Transferences in the last second:
    @Query(value = "SELECT * FROM transactions WHERE emisor_id = :id AND moment <= NOW() - INTERVAL 1 SECOND", nativeQuery = true)
    List<Object[]> transactionsInLastSecond(@Param("id") Long id);
}
