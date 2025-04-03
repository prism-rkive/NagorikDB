package com.samin.again.controller_reg;

import com.samin.again.DTO.DonationDTO;
import com.samin.again.entity.Donation;
import com.samin.again.service.DonationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/donations")
public class DonationController {

    @Autowired
    private DonationService donationService;

    // Create a new donation associated with a specific event
    @PostMapping("/{eventId}")
    public ResponseEntity<DonationDTO> createDonation(
            @PathVariable Long eventId,
            @RequestBody DonationDTO donationDTO) {

        donationDTO.setFundraisingEventId(eventId); // Associate event ID from the path
        Donation donation = donationService.createDonation(donationDTO);
        return ResponseEntity.ok(mapToDTO(donation));
    }

    // Get donations by event
    @GetMapping("/event/{eventId}")
    public ResponseEntity<List<DonationDTO>> getDonationsByEvent(@PathVariable Long eventId) {
        List<DonationDTO> donations = donationService.getDonationsByEvent(eventId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(donations);
    }

    // Get donations by user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DonationDTO>> getDonationsByUser(@PathVariable Long userId) {
        List<DonationDTO> donations = donationService.getDonationsByUser(userId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(donations);
    }

    // Helper method to map Donation to DonationDTO
    private DonationDTO mapToDTO(Donation donation) {
        DonationDTO dto = new DonationDTO();
        dto.setTxnId(donation.getTxnId());
        dto.setAmount(donation.getAmount());
        dto.setTime(donation.getTime());
        dto.setFundraisingEventId(donation.getFundraisingEvent().getEventId());
        dto.setUserId(donation.getUser().getNID());
        return dto;
    }
    @GetMapping("/grouped-by-date")
    public ResponseEntity<List<DonationDTO>> getDonationsGroupedByDate() {
        List<DonationDTO> donations = donationService.getDonationsGroupedByDate();
        return ResponseEntity.ok(donations);
    }
}

