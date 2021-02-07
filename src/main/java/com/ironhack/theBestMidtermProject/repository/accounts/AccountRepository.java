package com.ironhack.theBestMidtermProject.repository.accounts;

import com.ironhack.theBestMidtermProject.model.accounts.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
}
