package com.samin.again.service;

import com.samin.again.entity.Complaint;
import com.samin.again.entity.Emergency;
import com.samin.again.entity.User;
import com.samin.again.repository_reg.ComplaintRepository;
import com.samin.again.repository_reg.EmergencyRepository;
import com.samin.again.repository_reg.RegistrationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service


public class UserService {
    @Autowired
    private RegistrationRepo userRepository;

    // Create or update a user
    public User saveUser(User user) {
        System.out.println("Saving user: " + user); // Debugging
        return userRepository.save(user);
    }

    // Get all users
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    // Get a user by ID
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    // Delete a user by ID
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    // Custom logic: Authenticate a user
    public Optional<User> authenticateUser(Long nid, String password) {
        return userRepository.findById(nid)
                .filter(user -> user.getPassword().equals(password));
    }
    public long getActiveUsersCount() {
        return userRepository.count(); // Adjust logic if "active" is defined differently.
    }
    //fetch emergency
    @Autowired
    private EmergencyRepository emergencyRepository;

    public List<Emergency> getEmergenciesByUser(Long nid) {
        System.out.println("Fetching user with NID: ");

        return emergencyRepository.findByUserNidOrderByTimepostedDesc(nid);
    }
    @Autowired
    private ComplaintRepository complaintRepository;

    public List<Complaint> getComplaintsByUser(Long nid) {
        System.out.println("Fetching user with NID: ");
        return complaintRepository.findByUserNidOrderByPostedAtDesc(nid);
    }






}


