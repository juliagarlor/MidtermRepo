package com.ironhack.theBestMidtermProject.repository.users;

import com.ironhack.theBestMidtermProject.model.users.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.*;

@Repository
public interface ThirdPartyRepository extends JpaRepository<ThirdParty, Long> {
}
