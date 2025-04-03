package com.samin.again.service;

import com.samin.again.entity.Complaint;
import com.samin.again.entity.Cstatus;
import com.samin.again.entity.Emergency;
import com.samin.again.entity.User;
import com.samin.again.repository_reg.ComplaintRepository;
import com.samin.again.repository_reg.EmergencyRepository;
import com.samin.again.repository_reg.RegistrationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.samin.again.entity.Status.SUBMITTED;
@Service
public class ComplaintService {
    @Autowired
    private ComplaintRepository complaintRepository;
    @Autowired
    private RegistrationRepo userRepository;
    public Complaint reportComplaint(Complaint complaint) {
        // Fetch the user based on the userId
        Long userNid = complaint.getUser().getNID();
        if (userNid == null) {
            throw new RuntimeException("User information (NID) is missing in the request.");
        }

        // Retrieve the User from the database using the NID
        User user = userRepository.findById(userNid)
                .orElseThrow(() -> new RuntimeException("User not found. Please log in to submit an emergency."));

        // Set the user and other properties for the emergency
        complaint.setUser(user); // Link the user to the emergency
        complaint.setStatus(Cstatus.SUBMITTED); // Set the default status
        complaint.setPostedAt(LocalDateTime.now()); // Set the current timestamp

        // Save and return the emergency
        return complaintRepository.save(complaint);

    }
    public Optional<Complaint> getComplaintsById(Long id) {
        return complaintRepository.findById(id);
    }
    public Complaint saveComplaint(Complaint complaint) {
        System.out.println("Saving emergency: " + complaint); // Debugging
        return complaintRepository.save(complaint);
    }
    public Integer todayComplaint(){
        return complaintRepository.countComplaintToday();
    }
    public Integer todayResolved(){
        return complaintRepository.countResolvedToday();
    }
}
