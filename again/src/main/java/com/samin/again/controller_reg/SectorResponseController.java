package com.samin.again.controller_reg;
import com.samin.again.entity.*;
import com.samin.again.service.SectorResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.lang.String;
@RestController
@RequestMapping("/api/sector")
public class SectorResponseController {
    @Autowired
    private SectorResponseService sectorService;

    @PostMapping("/login")
    public ResponseEntity<SectorResponseTeam> authenticateUser(@RequestParam String response_id, @RequestParam String password) {
        Optional<SectorResponseTeam> sectorResponseOpt = sectorService.authenticateUser(response_id, password);
        return sectorResponseOpt.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }

    @GetMapping("/complaint/{s_id}")
    public ResponseEntity<List<Complaint>> getComplaintsForTeam(@PathVariable String s_id) {
        Optional<SectorResponseTeam> sectorTeam = sectorService.getATeamById(s_id);
        String sector = null;
        if (sectorTeam.isPresent()) {
            sector = sectorTeam.get().getResp_sector();
        }
        List<Complaint> complaints = sectorService.getUnassignedComplaintsBySector(sector);

        if (complaints.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(complaints);
    }
}


