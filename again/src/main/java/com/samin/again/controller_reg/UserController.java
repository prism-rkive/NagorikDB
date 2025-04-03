package com.samin.again.controller_reg;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.samin.again.DTO.UserDTO;
import com.samin.again.entity.Complaint;
import com.samin.again.entity.Emergency;
import com.samin.again.entity.User;
import com.samin.again.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpSession;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    // Register a new user
    @PostMapping
    public User registerUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    // Get all users
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    // Get a user by ID
    @GetMapping("/{nid}")
    public Optional<User> getUserById(@PathVariable Long nid) {
        return userService.getUserById(nid);
    }

    // Delete a user by ID
    @DeleteMapping("/{nid}")
    public void deleteUser(@PathVariable Long nid) {
        userService.deleteUser(nid);
    }

    // Update a user by ID
    @PutMapping("/{nid}")
    public User updateUser(@PathVariable Long nid, @RequestBody User user) {
        user.setNID(nid);
        return userService.saveUser(user);
    }
    @GetMapping("/emergency/{nid}")
    public ResponseEntity<List<Emergency>> getEmergenciesForUser(@PathVariable Long nid) {
        List<Emergency> emergencies = userService.getEmergenciesByUser(nid);
        if (emergencies.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(emergencies);
    }
    @GetMapping("/complaint/{nid}")
    public ResponseEntity<List<Complaint>> getComplaintsForUser(@PathVariable Long nid) {
        List<Complaint> complains = userService.getComplaintsByUser(nid);
        if (complains.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(complains);
    }





    @PostMapping("/authenticate")
    public ResponseEntity<UserDTO> authenticateUser(@RequestBody Map<String, Object> loginRequest) {
        // Extract nid and password from the request
        Long nid = Long.parseLong(loginRequest.get("nid").toString());
        String password = loginRequest.get("password").toString();

        // Authenticate user
        Optional<User> userOpt = userService.authenticateUser(nid, password);

        // If authentication succeeds, return UserDTO with both nid and name
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            UserDTO userDTO = new UserDTO(user.getNID(), user.getName()); // Pass nid and name to DTO
            return ResponseEntity.ok(userDTO);
        }

        // If authentication fails, return 401 Unauthorized
        return ResponseEntity.status(401).build();
    }

    @GetMapping("/active-users-count")
    public long getActiveUsersCount() {
        return userService.getActiveUsersCount();
    }












}

