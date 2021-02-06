package com.ironhack.theBestMidtermProject.repository.users;

import com.ironhack.theBestMidtermProject.model.users.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

import java.util.*;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPrincipalAccountsId(long id);
    Optional<User> findBySecondaryAccountsId(long id);
}
