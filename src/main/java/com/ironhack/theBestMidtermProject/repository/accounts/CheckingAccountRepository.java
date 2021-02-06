package com.ironhack.theBestMidtermProject.repository.accounts;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface CheckingAccountRepository extends JpaRepository<CheckingAccount, Long> {
}
