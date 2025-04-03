package com.samin.again.service;

import java.util.List;
import java.util.Optional;

import com.samin.again.entity.Admin;
import com.samin.again.repository_reg.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service 
public class AdminService {
	@Autowired
    private AdminRepository adminRepository;

    // Create or update an admin
    public Admin saveAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

    // Get all admins
    public List<Admin> getAllAdmins() {
        return adminRepository.findAll();
    }

    // Get an admin by ID
    public Optional<Admin> getAdminById(Long id) {
        return adminRepository.findById(id);
    }

    // Delete an admin by ID
    public void deleteAdmin(Long id) {
        adminRepository.deleteById(id);
    }
    // Authenticate admin by email and password
    public Optional<Admin> authenticateAdmin(String email, String password) {
        Optional<Admin> admin = adminRepository.findByEmail(email);
        if (admin.isPresent() && admin.get().getAdminPassword().equals(password)) {
            return admin;
        }
        return Optional.empty();
    }
    

}
