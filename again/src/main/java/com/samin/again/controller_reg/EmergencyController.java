package com.samin.again.controller_reg;

import com.samin.again.entity.*;

import com.samin.again.repository_reg.AreaResponseRepository;
import com.samin.again.repository_reg.EmergencyRepository;
import com.samin.again.service.AreaResponseService;
import com.samin.again.service.EmergencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
    @RequestMapping("/api/emergency")
    public class EmergencyController {
    @Autowired
    private EmergencyRepository emergencyRepository;
    @Autowired
    private EmergencyService emergencyService;

    // Emergency reported
        @PostMapping
        public Emergency newEmergency(@RequestBody Emergency emergency) {

            return emergencyService.reportEmergency(emergency);
    }

    //update
    @PostMapping("/update/assigned/{id}")
    public ResponseEntity<?> updateEmergencyStatus(@PathVariable Long id) {
        Optional<Emergency> emergencyOptional = emergencyRepository.findById(id);
        if (emergencyOptional.isPresent()) {
            Emergency emergency= emergencyOptional.get();
            emergency.setEstatus(Status.ASSIGNED);
            emergencyRepository.save(emergency);
            return ResponseEntity.ok(Map.of("message", "Status updated successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Emergency not found"));
        }
    }
    @PostMapping("/update/rescued/{id}")
    public ResponseEntity<?> updateRescuedEmergencyStatus(@PathVariable Long id) {
        Optional<Emergency> emergencyOptional = emergencyRepository.findById(id);
        if (emergencyOptional.isPresent()) {
            Emergency emergency= emergencyOptional.get();
            emergency.setEstatus(Status.RESCUED);
            emergencyRepository.save(emergency);
            return ResponseEntity.ok(Map.of("message", "Status updated successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Emergency not found"));
        }
    }
    @PostMapping("/update/resolved/{id}")
    public ResponseEntity<?> updateResolvedEmergencyStatus(@PathVariable Long id) {
        Optional<Emergency> emergencyOptional = emergencyRepository.findById(id);
        if (emergencyOptional.isPresent()) {
            Emergency emergency= emergencyOptional.get();
            emergency.setEstatus(Status.RESOLVED);
            emergencyRepository.save(emergency);
            return ResponseEntity.ok(Map.of("message", "Status updated successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Emergency not found"));
        }
    }
    @PostMapping("/update/delayed/{id}")
    public ResponseEntity<?> updateDelayedEmergencyStatus(@PathVariable Long id) {
        Optional<Emergency> emergencyOptional = emergencyRepository.findById(id);
        if (emergencyOptional.isPresent()) {
            Emergency emergency= emergencyOptional.get();
            emergency.setEstatus(Status.DELAYED);
            emergencyRepository.save(emergency);
            return ResponseEntity.ok(Map.of("message", "Status updated successfully"));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("message", "Emergency not found"));
        }
    }

//count
        @GetMapping("/data/count")
    public ResponseEntity<Map<String, Object>> getEmergencyCountsByArea() {
        // Query the data
        List<Object[]> results = emergencyRepository.countEmergenciesByArea();

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
        List<Object[]> results = emergencyRepository.countEmergenciesByCategory();

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
        Integer count = emergencyService.todayEmergency();
        return ResponseEntity.ok(count);
    }
    @GetMapping("/count/today/rescued")
    public ResponseEntity<Integer> getTodayRescueCount() {
        Integer count = emergencyService.todayRescued();
        return ResponseEntity.ok(count);
    }
    @Autowired
    private AreaResponseService areaService;

    @GetMapping("/area/bar/{a_id}")
    public ResponseEntity<Map<String, Object>> getEmergencyCountofDistrictByCategory(@PathVariable String a_id) {
        // Query the data
        Optional<AreaResponseTeam> areaTeam= areaService.getATeamById(a_id);
        String district=null;
        if (areaTeam.isPresent()) {
            district = areaTeam.get().getResp_district();
        }

        List<Object[]> results = emergencyRepository.countEmergenciesByCategoryInDistrict(district);

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
    @GetMapping("/area/pie/{a_id}")
    public ResponseEntity<Map<String, Object>> getEmergencyCountofDistrictByStatus(@PathVariable String a_id) {
        // Query the data
        Optional<AreaResponseTeam> areaTeam= areaService.getATeamById(a_id);
        System.out.println("District: " + a_id);

        String district=null;
        if (areaTeam.isPresent()) {
            district = areaTeam.get().getResp_district();
        }
        System.out.println("District: " + district);

        List<Object[]> results = emergencyRepository.countEmergenciesByEstatusInDistrict(district);

        // Process results
        List<String> labels = results.stream()
                .map(row -> ((Status) row[0]).name()) // Convert enum to String
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
    @GetMapping("/count/today/{a_id}")
    public ResponseEntity<Integer> getTodayCountByTeam(@PathVariable String a_id) {
        Optional<AreaResponseTeam> areaTeam= areaService.getATeamById(a_id);
        String district=null;
        if (areaTeam.isPresent()) {
            district = areaTeam.get().getResp_district();
        }
        Integer count = emergencyRepository.countRescuedInDistrictToday(district);
        return ResponseEntity.ok(count);
    }
    @GetMapping("/count/today/rescued/{a_id}")
    public ResponseEntity<Integer> getTodayRescueCountbyTeam(@PathVariable String a_id) {
        Optional<AreaResponseTeam> areaTeam= areaService.getATeamById(a_id);
        String district=null;
        if (areaTeam.isPresent()) {
            district = areaTeam.get().getResp_district();
        }
        Integer count = emergencyRepository.countRescuedInDistrictToday(district);
        return ResponseEntity.ok(count);
    }
    @GetMapping("/count/percent/{a_id}")
    public ResponseEntity<Double> getPerformancebyTeam(@PathVariable String a_id) {
        Optional<AreaResponseTeam> areaTeam= areaService.getATeamById(a_id);
        String district=null;
        if (areaTeam.isPresent()) {
            district = areaTeam.get().getResp_district();
        }
        Integer tot = emergencyRepository.countReportedInDistrict(district);
        Integer rescued=emergencyRepository.countRescuedInDistrict(district);
        double count=0.0;
        if(tot!=0)count=((double) rescued /tot)*100;
        count = Math.round(count * 100.0) / 100.0; // Multiply by 100, round, and then divide by 100

        return ResponseEntity.ok(count);
    }
}










