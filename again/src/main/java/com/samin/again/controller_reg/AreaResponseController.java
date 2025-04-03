package com.samin.again.controller_reg;
import com.samin.again.entity.*;
import com.samin.again.service.AreaResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.lang.String;
@RestController
@RequestMapping("/api/area")
public class AreaResponseController {
    @Autowired
    private AreaResponseService areaService;
    @PostMapping("/login")
    public ResponseEntity<AreaResponseTeam> authenticateUser(@RequestParam String  response_id, @RequestParam String password) {
        Optional<AreaResponseTeam>areaResponseOpt = areaService.authenticateUser(response_id, password);
        return areaResponseOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }
    @GetMapping("/teams/{a_id}")
    public ResponseEntity<List<RescueTeam>> getMyTeams(@PathVariable String a_id){
        List<RescueTeam> rescueTeams=areaService.getMyTeams(a_id);
        if (rescueTeams.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(rescueTeams);

    }

    @GetMapping("/subemergency/{a_id}")
    public ResponseEntity<List<Emergency>> getEmergenciesForUser(@PathVariable String a_id) {
        Optional<AreaResponseTeam> areaTeam= areaService.getATeamById(a_id);
        String district=null;
        if (areaTeam.isPresent()) {
            district = areaTeam.get().getResp_district();
        }
        List<Emergency> emergencies = areaService.getUnassignedEmergenciesByDistrict(district);

        if (emergencies.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(emergencies);
    }
    @GetMapping("/resolved/{a_id}")
    public ResponseEntity<List<Emergency>> getResolvedEmergenciesForUser(@PathVariable String a_id) {
        Optional<AreaResponseTeam> areaTeam= areaService.getATeamById(a_id);
        String district=null;
        if (areaTeam.isPresent()) {
            district = areaTeam.get().getResp_district();
        }
        List<Emergency> emergencies = areaService.getResolvedEmergenciesByDistrict(district);

        if (emergencies.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(emergencies);
    }
    @GetMapping("/assigned/{a_id}")
    public ResponseEntity<List<Emergency>> getAssignedEmergenciesForUser(@PathVariable String a_id) {
        Optional<AreaResponseTeam> areaTeam= areaService.getATeamById(a_id);
        String district=null;
        if (areaTeam.isPresent()) {
            district = areaTeam.get().getResp_district();
        }
        List<Emergency> emergencies = areaService.getAssignedEmergenciesByDistrict(district);

        if (emergencies.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(emergencies);
    }
    @GetMapping("/rescued/{a_id}")
    public ResponseEntity<List<Emergency>> getRescuedEmergenciesForUser(@PathVariable String a_id) {
        Optional<AreaResponseTeam> areaTeam= areaService.getATeamById(a_id);
        String district=null;
        if (areaTeam.isPresent()) {
            district = areaTeam.get().getResp_district();
        }
        List<Emergency> emergencies = areaService.getRescuedEmergenciesByDistrict(district);

        if (emergencies.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(emergencies);
    }
    @GetMapping("/delayed/{a_id}")
    public ResponseEntity<List<Emergency>> getDelayedEmergenciesForUser(@PathVariable String a_id) {
        Optional<AreaResponseTeam> areaTeam= areaService.getATeamById(a_id);
        String district=null;
        if (areaTeam.isPresent()) {
            district = areaTeam.get().getResp_district();
        }
        List<Emergency> emergencies = areaService.getDelayedEmergenciesByDistrict(district);

        if (emergencies.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(emergencies);
    }



}


