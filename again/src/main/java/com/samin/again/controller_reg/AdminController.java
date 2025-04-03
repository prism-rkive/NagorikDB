package com.samin.again.controller_reg;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.samin.again.DTO.SurveyDTO;
import com.samin.again.entity.Admin;
import com.samin.again.entity.Survey;
import com.samin.again.service.AdminService;
import com.samin.again.service.SurveyService;
import com.samin.again.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private SurveyService surveyService;
    
    @Autowired
    private UserService userService;

    // Endpoint to log in an admin
    @PostMapping("/login")
    public ResponseEntity<?> loginAdmin(@RequestBody Admin adminRequest) {
        Optional<Admin> authenticatedAdmin = adminService.authenticateAdmin(adminRequest.getEmail(), adminRequest.getAdminPassword());

        if (authenticatedAdmin.isPresent()) {
            Admin admin = authenticatedAdmin.get();
            // Return JSON response for success, including admin ID and message
            return ResponseEntity.ok(Map.of(
                "message", "Login successful",
                "id", admin.getId() // Add admin ID to response
            ));
        } else {
            // Return JSON response for error
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body(Map.of("error", "Invalid email or password"));
        }
    }



    // Endpoint to create or update an admin
    @PostMapping
    public ResponseEntity<Admin> createOrUpdateAdmin(@RequestBody Admin admin) {
        if (admin.getEmail() == null || admin.getAdminPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        Admin savedAdmin = adminService.saveAdmin(admin);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAdmin);
    }

    // Endpoint to retrieve all admins
    @GetMapping
    public ResponseEntity<List<Admin>> getAllAdmins() {
        List<Admin> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(admins);
    }

    // Endpoint to retrieve an admin by ID
    @GetMapping("/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable Long id) {
        Optional<Admin> admin = adminService.getAdminById(id);
        return admin.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Endpoint to delete an admin by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAdmin(@PathVariable Long id) {
        Optional<Admin> admin = adminService.getAdminById(id);
        if (admin.isPresent()) {
            adminService.deleteAdmin(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/api/surveys/admin/{adminId}")
    public List<SurveyDTO> getSurveysByAdmin(@PathVariable Long adminId) {
        List<Survey> surveys = surveyService.getSurveysByAdmin(adminId);
        return surveys.stream()
                .map(survey -> new SurveyDTO(survey.getSurveyId(), survey.getQuestion(), survey.getStartTime(), survey.getEndTime()))
                .collect(Collectors.toList());
    }
    @GetMapping("/api/surveys")
    public List<SurveyDTO> getAllSurveys() {
        List<Survey> surveys = surveyService.getAllSurveys();
        return surveys.stream()
                .map(survey -> new SurveyDTO(survey.getSurveyId(), survey.getQuestion(), survey.getStartTime(), survey.getEndTime()))
                .collect(Collectors.toList());
    }
   
    
}
