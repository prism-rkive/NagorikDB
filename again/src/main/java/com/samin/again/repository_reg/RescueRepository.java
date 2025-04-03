package com.samin.again.repository_reg;


import com.samin.again.DTO.RescueDTO;
import com.samin.again.entity.Emergency;
import com.samin.again.entity.RescueTeam;
import com.samin.again.entity.RescueTeamId;
import com.samin.again.entity.Rstatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@Repository
public interface RescueRepository extends JpaRepository<RescueTeam, RescueTeamId> {

       List<RescueTeam> findByResponseTeamId(String responseTeamId);
       List<RescueTeam> findByResponseTeamResponseIdAndAvailability(String response_id, Rstatus availibility);
}

