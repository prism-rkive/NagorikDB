package com.samin.again.controller_reg;

import com.samin.again.entity.*;

import com.samin.again.repository_reg.ComplaintRepository;
import com.samin.again.repository_reg.EmergencyRepository;
import com.samin.again.repository_reg.RegistrationRepo;
import com.samin.again.service.AreaResponseService;
import com.samin.again.service.ComplaintService;
import com.samin.again.service.EmergencyService;
import com.samin.again.service.SectorResponseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/complaint")
public class ComplaintController {
    @Autowired
    private ComplaintRepository complaintRepository;
    @Autowired
    private RegistrationRepo userRepository;
    @Autowired
    private ComplaintService complaintService;
    @Autowired
    private SectorResponseService sectorService;

    // Emergency reported


        @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public Complaint newComplaint(
                @RequestParam("sector") String sector,
                @RequestParam("department") String department,
                @RequestParam("contact_no") String contactNo,
                @RequestParam("details") String details,
                @RequestParam("nid") Long nid,
                @RequestParam(value = "attachment", required = false) MultipartFile attachment
        ) {
            // Fetch user from NID
            User user = userRepository.findById(nid)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            // Create a new Complaint entity
            Complaint complaint = new Complaint();
            complaint.setSector(sector);
            complaint.setDepartment(department);
            complaint.setContact_no(contactNo);
            complaint.setDetails(details);
            complaint.setUser(user);
            complaint.setStatus(Cstatus.SUBMITTED);
            complaint.setPostedAt(LocalDateTime.now());

            // Handle file upload (optional)
            if (attachment != null && !attachment.isEmpty()) {
                try {
                    complaint.setAttachment(attachment.getBytes()); // Save file as byte array
                } catch (IOException e) {
                    throw new RuntimeException("Failed to process attachment", e);
                }
            }

            // Save and return the complaint
            return complaintRepository.save(complaint);
        }
    @GetMapping("/{id}/attachment")
    public ResponseEntity<byte[]> getComplaintAttachment(@PathVariable Long id) {
        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        if (complaint.getAttachment() == null) {
            return ResponseEntity.notFound().build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename("attachment.pdf") // Replace with dynamic filename if available
                .build());

        return new ResponseEntity<>(complaint.getAttachment(), headers, HttpStatus.OK);
    }
    @GetMapping("/processing/{s_id}")
    public ResponseEntity<List<Complaint>> getProcessedComplaintForUser(@PathVariable String s_id) {
        Optional<SectorResponseTeam> sectorTeam= sectorService.getATeamById(s_id);
        String sector=null;
        if (sectorTeam.isPresent()) {
            sector = sectorTeam.get().getResp_sector();
        }
        List<Complaint> complain = sectorService.getProcessingComplaintBySector(sector);

        if (complain.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(complain);
    }
    @GetMapping("/assigned/{s_id}")
    public ResponseEntity<List<Complaint>> getAssignedComplaintForUser(@PathVariable String s_id) {
        Optional<SectorResponseTeam> sectorTeam= sectorService.getATeamById(s_id);
        String sector=null;
        if (sectorTeam.isPresent()) {
            sector = sectorTeam.get().getResp_sector();
        }
        List<Complaint> complain = sectorService.getAssignedComplaintBySector(sector);

        if (complain.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(complain);
    }
    @GetMapping("/unassigned/{s_id}")
    public ResponseEntity<List<Complaint>> getUnassignedComplaintForUser(@PathVariable String s_id) {
        Optional<SectorResponseTeam> sectorTeam= sectorService.getATeamById(s_id);
        String sector=null;
        if (sectorTeam.isPresent()) {
            sector = sectorTeam.get().getResp_sector();
        }
        List<Complaint> complain = sectorService.getUnassignedComplaintsBySector(sector);

        if (complain.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(complain);
    }
    @GetMapping("/resolved/{s_id}")
    public ResponseEntity<List<Complaint>> getResolvedComplaintForUser(@PathVariable String s_id) {
        Optional<SectorResponseTeam> sectorTeam= sectorService.getATeamById(s_id);
        String sector=null;
        if (sectorTeam.isPresent()) {
            sector = sectorTeam.get().getResp_sector();
        }
        List<Complaint> complain = sectorService.getResolvedComplaintBySector(sector);

        if (complain.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(complain);
    }
    @PostMapping("/update/process/{id}")
    public ResponseEntity<?> updateComplaintStatus(@PathVariable Long id) {
        Optional<Complaint> complaintOptional = complaintRepository.findById(id);
        if (complaintOptional.isPresent()) {
            Complaint complaint = complaintOptional.get();
            complaint.setStatus(Cstatus.PROCESSING);
            complaintRepository.save(complaint);
            return ResponseEntity.ok(Map.of("message", "Status updated successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Complaint not found"));
        }
    }
    @PostMapping("/update/resolved/{id}")
    public ResponseEntity<?> updateComplaintStatusResolved(@PathVariable Long id) {
        Optional<Complaint> complaintOptional = complaintRepository.findById(id);
        if (complaintOptional.isPresent()) {
            Complaint complaint = complaintOptional.get();
            complaint.setStatus(Cstatus.RESOLVED);
            complaintRepository.save(complaint);
            return ResponseEntity.ok(Map.of("message", "Status updated successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Complaint not found"));
        }
    }
    @PostMapping("/update/assigned/{id}")
    public ResponseEntity<?> updateComplaintStatusAssigned(@PathVariable Long id) {
        Optional<Complaint> complaintOptional = complaintRepository.findById(id);
        if (complaintOptional.isPresent()) {
            Complaint complaint = complaintOptional.get();
            complaint.setStatus(Cstatus.ASSIGNED);
            complaintRepository.save(complaint);
            return ResponseEntity.ok(Map.of("message", "Status updated successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Complaint not found"));
        }
    }




    @GetMapping("/data/count")
    public ResponseEntity<Map<String, Object>> getEmergencyCountsByArea() {
        // Query the data
        List<Object[]> results = complaintRepository.countComplaintBySector();

        // Process results
        List<String> labels = results.stream()
                .map(row -> (String) row[0]) // Area
                .toList();
        List<Long> counts = results.stream()
                .map(row -> ((Number) row[1]).longValue()) // Count
                .toList();

        // Prepare response
        Map<String, Object> response = Map.of("labels", labels, "counts", counts);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/data/pie")
    public ResponseEntity<Map<String, Object>> getEmergencyCountsByCategory() {
        // Query the data
        List<Object[]> results = complaintRepository.countComplaintByDepartment();

        // Process results
        List<String> labels = results.stream()
                .map(row -> (String) row[0]) // Area
                .toList();
        List<Long> counts = results.stream()
                .map(row -> ((Number) row[1]).longValue()) // Count
                .toList();

        // Prepare response
        Map<String, Object> response = Map.of("labels", labels, "counts", counts);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/count/today")
    public ResponseEntity<Integer> getTodayEmergencyCount() {
        Integer count = complaintService.todayComplaint();
        return ResponseEntity.ok(count);
    }
    @GetMapping("/count/today/resolved")
    public ResponseEntity<Integer> getTodayRescueCount() {
        Integer count = complaintService.todayResolved();
        return ResponseEntity.ok(count);
    }


    @GetMapping("/sector/bar/{s_id}")
    public ResponseEntity<Map<String, Object>> getcomplaintCountofSectorByDepartment(@PathVariable String s_id) {
        // Query the data
        Optional<SectorResponseTeam> sectorTeam=sectorService.getATeamById(s_id);
        String sector=null;
        if (sectorTeam.isPresent()) {
            sector = sectorTeam.get().getResp_sector();
        }

        List<Object[]> results = complaintRepository.countComplaintByDepartmentInSector(sector);

        // Process results
        List<String> labels = results.stream()
                .map(row -> (String) row[0]) // Area
                .toList();
        List<Long> counts = results.stream()
                .map(row -> ((Number) row[1]).longValue()) // Count
                .toList();

        // Prepare response
        Map<String, Object> response = Map.of("labels", labels, "counts", counts);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/sector/pie/{s_id}")
    public ResponseEntity<Map<String, Object>> getComplaintCountofSectorByStatus(@PathVariable String s_id) {
        // Query the data
        Optional<SectorResponseTeam> sectorTeam= sectorService.getATeamById(s_id);

        String sector=null;
        if (sectorTeam.isPresent()) {
            sector = sectorTeam.get().getResp_sector();
        }

        List<Object[]> results = complaintRepository.countComplaintByStatusInSector(sector);

        // Process results
        List<String> labels = results.stream()
                .map(row -> ((Cstatus) row[0]).name()) // Convert enum to String
                .toList();
        List<Long> counts = results.stream()
                .map(row -> ((Number) row[1]).longValue()) // Count
                .toList();

        // Prepare response
        Map<String, Object> response = Map.of("labels", labels, "counts", counts);
        // Log the response to the console
        System.out.println("Labels: " + labels);
        System.out.println("Counts: " + counts);
        System.out.println("Response Map: " + response);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/count/today/{s_id}")
    public ResponseEntity<Integer> getTodayCountByTeam(@PathVariable String s_id) {
        Optional<SectorResponseTeam> sectorTeam= sectorService.getATeamById(s_id);

        String sector=null;
        if (sectorTeam.isPresent()) {
            sector = sectorTeam.get().getResp_sector();
        }
        Integer count = complaintRepository.countReportedInSectorToday(sector);
        return ResponseEntity.ok(count);
    }
    @GetMapping("/count/today/resolved/{s_id}")
    public ResponseEntity<Integer> getTodayResolvedCountbyTeam(@PathVariable String s_id) {
        Optional<SectorResponseTeam> sectorTeam= sectorService.getATeamById(s_id);

        String sector=null;
        if (sectorTeam.isPresent()) {
            sector = sectorTeam.get().getResp_sector();
        }
        Integer count = complaintRepository.countResolvedInSectorToday(sector);
        return ResponseEntity.ok(count);
    }
    @GetMapping("/count/percent/{s_id}")
    public ResponseEntity<Double> getPerformancebyTeam(@PathVariable String s_id) {
        Optional<SectorResponseTeam> sectorTeam= sectorService.getATeamById(s_id);

        String sector=null;
        if (sectorTeam.isPresent()) {
            sector = sectorTeam.get().getResp_sector();
        }
        Integer tot = complaintRepository.countReportedInSector(sector);
        Integer rescued=complaintRepository.countResolvedInSector(sector);
        double count=0.0;
        if(tot!=0)count=((double) rescued /tot)*100;
        count = Math.round(count * 100.0) / 100.0; // Multiply by 100, round, and then divide by 100

        return ResponseEntity.ok(count);
    }
}
