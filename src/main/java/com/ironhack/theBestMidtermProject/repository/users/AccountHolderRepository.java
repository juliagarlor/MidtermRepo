package com.ironhack.theBestMidtermProject.repository.users;

import com.ironhack.theBestMidtermProject.model.users.*;
import com.ironhack.theBestMidtermProject.utils.classes.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long> {
//    Optional<User> findByPrincipalAccountsId(long id);
//    Optional<User> findBySecondaryAccountsId(long id);
//    Optional<User> findByNameAndAccountsIdIs(Name name, long id);
}
