package com.samin.again.service;

import com.samin.again.entity.*;
import com.samin.again.repository_reg.RescueRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class RescueService {
    @Autowired
    private RescueRepository rescueRepository;
    public List<RescueTeam> getRescueTeamsById(String response_id) {
        List<RescueTeam> rescueTeams = rescueRepository.findByResponseTeamId(response_id);

        // Print the result for debugging
        System.out.println("Fetched Rescue Teams for responseTeamId: " + response_id);

        if (rescueTeams.isEmpty()) {
            System.out.println("No Rescue Teams found for the given responseTeamId.");
        } else {
            for (RescueTeam team : rescueTeams) {
                System.out.println("Team ID: " + team.getTeam_id() + ", Name: " + team.getName() + ", Availability: " + team.getAvailability());
            }
        }

        return rescueTeams;    }
    public List<RescueTeam> getAvailableRescueTeamsById(String response_id) {
        List<RescueTeam> rescueTeams = rescueRepository.findByResponseTeamResponseIdAndAvailability(response_id,Rstatus.AVAILABLE);

        // Print the result for debugging
        System.out.println("Fetched Rescue Teams for responseTeamId: " + response_id);

        if (rescueTeams.isEmpty()) {
            System.out.println("No Rescue Teams found for the given responseTeamId.");
        } else {
            for (RescueTeam team : rescueTeams) {
                System.out.println("Team ID: " + team.getTeam_id() + ", Name: " + team.getName() + ", Availability: " + team.getAvailability());
            }
        }

        return rescueTeams;    }
    public RescueTeam getTeam(String a_id,Long team_id) {
        RescueTeamId id = new RescueTeamId(team_id, a_id);
        return rescueRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Rescue team not found"));
    }



}
