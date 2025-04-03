package com.samin.again.repository_reg;

import com.samin.again.entity.RescueTeamContact;
import com.samin.again.entity.RescueTeamContactId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RescueContactRepository extends JpaRepository<RescueTeamContact, RescueTeamContactId> {

    @Query("SELECT r.team_id2 AS teamId, " +
            "GROUP_CONCAT(r.r_contact) AS contacts " +
            "FROM RescueTeamContact r " +
            "WHERE r.a2_id = :a2_id " +
            "GROUP BY r.team_id2")
    List<Object[]> findTeamIdAndContactsByA2Id(@Param("a2_id") String a2_id);
}
