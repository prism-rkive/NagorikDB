package com.samin.again.repository_reg;

import com.samin.again.entity.AreaResponseTeam;
import com.samin.again.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AreaResponseRepository extends JpaRepository<AreaResponseTeam,String> {
}
