package com.samin.again.repository_reg;

import com.samin.again.entity.ResponseTeam;
import com.samin.again.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResponseRepository extends JpaRepository<ResponseTeam,String> {
}
