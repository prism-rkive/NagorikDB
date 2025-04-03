package com.samin.again.repository_reg;

import com.samin.again.entity.ResponseContact;
import com.samin.again.entity.ResponseContactId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResponseContactRepository extends JpaRepository<ResponseContact, ResponseContactId> {

    @Query("SELECT r.responseId AS teamId, " +
            "GROUP_CONCAT(r.contact) AS contacts " +
            "FROM ResponseContact r " +
            "GROUP BY r.responseId")
    List<Object[]> findTeamContactsByTeamId(@Param("responseId") String responseId);
}

