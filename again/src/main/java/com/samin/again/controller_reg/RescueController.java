package com.samin.again.controller_reg;
import com.samin.again.DTO.RescueDTO;
import com.samin.again.entity.*;
import com.samin.again.repository_reg.RescueContactRepository;
import com.samin.again.repository_reg.RescueRepository;
import com.samin.again.service.AreaResponseService;
import com.samin.again.service.EmergencyService;
import com.samin.again.service.RescueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.lang.String;
@RestController
@RequestMapping("/api/rescue")

public class RescueController {

    @Autowired
    private RescueService rescueService;
    @Autowired
    private AreaResponseService areaService;
    @Autowired
    private EmergencyService emergencyService;
    @Autowired
    private RescueRepository rescueRepository;

    @GetMapping("/available/{a_id}")
    public ResponseEntity<List<RescueTeam>> getRescueTeams(@PathVariable String a_id) {
        Optional<AreaResponseTeam> areaTeam = areaService.getATeamById(a_id);
        /*String district=null;
        if (areaTeam.isPresent()) {
            district = areaTeam.get().getDistrict();
        }*/
        List<RescueTeam> teams = rescueService.getAvailableRescueTeamsById(a_id);

        if (teams.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(teams);
    }

    @PostMapping("/assign/{a_id}/{teamId}/{caseId}")
    public ResponseEntity<String> assignRescueTeam(@PathVariable String a_id, @PathVariable Long teamId, @PathVariable Long caseId) {
        RescueTeam rescueTeam = rescueService.getTeam(a_id, teamId);
        rescueTeam.setAvailability(Rstatus.ASSIGNED); // Ensure 'Rstatus' enum has 'ASSIGNED'
        // rescueService.saveTeam(rescueTeam);
        Optional<Emergency> emergencyOpt = emergencyService.getEmergencyById(caseId);
        if (emergencyOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Emergency not found.");
        }

        Emergency emergency = emergencyOpt.get();
        emergency.setEstatus(Status.ASSIGNED); // Ensure 'Rstatus' enum has 'ASSIGNED'
        emergencyService.saveEmergency(emergency);


        return ResponseEntity.ok("Rescue team assigned successfully.");


    }

    @PostMapping("/avail/{a_id}/{teamId}")
    public ResponseEntity<String> statusRescueTeam(@PathVariable String a_id, @PathVariable Long teamId) {
        RescueTeam rescueTeam = rescueService.getTeam(a_id, teamId);

        rescueTeam.setAvailability(Rstatus.AVAILABLE);
        rescueRepository.save(rescueTeam);
        System.out.println(rescueTeam.getAvailability());


        return ResponseEntity.ok("Rescue team assigned successfully.");


    }
    //@GetMapping("/contact/{a_id}")

    @Autowired
    private RescueContactRepository rescueContactRepository;
    @GetMapping("/contact/{a_id}")
    public List<Map<String, Object>> getTeamContacts(@PathVariable String a_id) {
        List<Object[]> results = rescueContactRepository.findTeamIdAndContactsByA2Id(a_id);

        List<Map<String, Object>> response = new ArrayList<>();
        for (Object[] result : results) {
            Long teamId = (Long) result[0];
            String contacts = (String) result[1];
            List<String> contactList = Arrays.asList(contacts.split(","));

            // Create a map for each teamId
            Map<String, Object> row = new HashMap<>();
            row.put("teamId", teamId);
            row.put("contacts", contactList);  // Store the contact list for the teamId

            response.add(row);
        }
        return response;
    }
}


