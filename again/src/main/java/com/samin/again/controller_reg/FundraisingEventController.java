package com.samin.again.controller_reg;


import com.samin.again.DTO.FundraisingEventDTO;
import com.samin.again.entity.Donation;

import com.samin.again.entity.FundraisingEvent;
import com.samin.again.service.FundraisingEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/events")
public class FundraisingEventController {

    @Autowired
    private FundraisingEventService fundraisingEventService;

    // Create or Update an event
    @PostMapping
    public ResponseEntity<FundraisingEventDTO> createOrUpdateEvent(@RequestBody FundraisingEventDTO fundraisingEventDTO) {
        FundraisingEvent event = fundraisingEventService.saveFundraisingEvent(fundraisingEventDTO);
        return ResponseEntity.ok(mapToDTO(event));
    }

    // Get all events
    /*@GetMapping
    public ResponseEntity<List<FundraisingEventDTO>> getAllEvents() {
        List<FundraisingEventDTO> events = fundraisingEventService.getAllEvents()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(events);
    }*/
    @GetMapping
    public ResponseEntity<List<FundraisingEventDTO>> getAllEvents() {
        List<FundraisingEventDTO> events = fundraisingEventService.getAllEvents()
                .stream()
                .map(event -> {
                    FundraisingEventDTO dto = mapToDTO(event);
                    // Calculate totalDonated and set it in the DTO
                    double totalDonated = event.getDonations()
                            .stream()
                            .mapToDouble(Donation::getAmount)
                            .sum();
                    dto.setTotalDonated(totalDonated);
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(events);
    }


    // Get events by admin
    @GetMapping("/admin/{adminId}")
    public ResponseEntity<List<FundraisingEventDTO>> getEventsByAdmin(@PathVariable Long adminId) {
        List<FundraisingEventDTO> events = fundraisingEventService.getEventsByAdmin(adminId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(events);
    }

    // Delete an event
    @DeleteMapping("/{eventId}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long eventId) {
        fundraisingEventService.deleteEvent(eventId);
        return ResponseEntity.ok("Event deleted successfully");
    }
    // Get event details by eventId
    @GetMapping("/{eventId}")
    public ResponseEntity<FundraisingEventDTO> getEventDetails(@PathVariable Long eventId) {
        FundraisingEvent event = fundraisingEventService.getEventById(eventId);
        if (event == null) {
            return ResponseEntity.notFound().build(); // Return 404 if event not found
        }

        FundraisingEventDTO dto = mapToDTO(event);
        // Calculate totalDonated and set it in the DTO
        double totalDonated = event.getDonations()
                .stream()
                .mapToDouble(Donation::getAmount)
                .sum();
        dto.setTotalDonated(totalDonated);

        return ResponseEntity.ok(dto);
    }

    // Helper method to map an entity to a DTO
    private FundraisingEventDTO mapToDTO(FundraisingEvent event) {
        FundraisingEventDTO dto = new FundraisingEventDTO();
        dto.setEventId(event.getEventId());
        dto.setCause(event.getCause());
        dto.setStartTime(event.getStartTime());
        dto.setEndTime(event.getEndTime());
        dto.setAdminId(event.getAdmin().getId()); // Only map admin ID to avoid full admin details
        return dto;
    }
    @GetMapping("/donations-by-date/{eventId}")
    public List<Object[]> getDonationsByDate(@PathVariable Long eventId) {
        return fundraisingEventService.getDonationsByDate(eventId);
    }

    @GetMapping("/total-donations-by-event")
    public List<Object[]> getTotalDonationsByEvent() {
        return fundraisingEventService.getTotalDonationsByEvent();
    }

    @GetMapping("/top-donors/{eventId}")
    public List<Object[]> getTopDonorsForEvent(@PathVariable Long eventId) {
        return fundraisingEventService.getTopDonorsForEvent(eventId);
    }

    @GetMapping("/total-donations-by-admin/{adminId}")
    public List<Object[]> getTotalDonationsByAdminEvents(@PathVariable Long adminId) {
        return fundraisingEventService.getTotalDonationsByAdminEvents(adminId);
    }

    @GetMapping("/donations-by-user")
    public List<Object[]> getDonationsByUser() {
        return fundraisingEventService.getDonationsByUser();
    }






}
