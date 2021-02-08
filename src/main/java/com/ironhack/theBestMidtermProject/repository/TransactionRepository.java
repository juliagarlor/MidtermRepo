package com.ironhack.theBestMidtermProject.repository;

import com.ironhack.theBestMidtermProject.model.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface TransactionRepository extends JpaRepository<Transactions, Long> {
}
