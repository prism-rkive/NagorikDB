package com.samin.again.repository_reg;

import java.util.Optional;

import com.samin.again.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AdminRepository  extends JpaRepository<Admin, Long>{
	 Optional<Admin> findByEmail(String email);

}
