package com.samin.again.service;

import com.samin.again.entity.Emergency;
import com.samin.again.entity.User;
import com.samin.again.repository_reg.EmergencyRepository;
import com.samin.again.repository_reg.RegistrationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.samin.again.entity.Status.SUBMITTED;
@Service
public class EmergencyService {
    @Autowired
    private EmergencyRepository emergencyRepository;
    @Autowired
    private RegistrationRepo userRepository;
    public Emergency reportEmergency(Emergency emergency) {
        // Fetch the user based on the userId
        Long userNid = emergency.getUser().getNID();
        System.out.println("Fetching user with NID: " + emergency.getUser().getNID());
// This will come from the frontend

        if (userNid == null) {
            throw new RuntimeException("User information (NID) is missing in the request.");
        }

        // Retrieve the User from the database using the NID
        User user = userRepository.findById(userNid)
                .orElseThrow(() -> new RuntimeException("User not found. Please log in to submit an emergency."));

        // Set the user and other properties for the emergency
        emergency.setUser(user); // Link the user to the emergency
        emergency.setEstatus(SUBMITTED); // Set the default status
        emergency.setTimeposted(LocalDateTime.now()); // Set the current timestamp
        System.out.println("Fetching user with NID: " + emergency.getTimeposted());

        // Save and return the emergency
        return emergencyRepository.save(emergency);

    }
    public Optional<Emergency> getEmergencyById(Long id) {
        return emergencyRepository.findById(id);
    }
    public Emergency saveEmergency(Emergency emergency) {
        System.out.println("Saving emergency: " + emergency); // Debugging
        return emergencyRepository.save(emergency);
    }
    public Integer todayEmergency(){
        return emergencyRepository.countEmergenciesToday();
    }
    public Integer todayRescued(){
        return emergencyRepository.countRescuedToday();
    }





}
