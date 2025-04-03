package com.samin.again.service;


import com.samin.again.DTO.DonationDTO;
import com.samin.again.entity.Donation;
import com.samin.again.entity.FundraisingEvent;
import com.samin.again.entity.User;
import com.samin.again.repository_reg.DonationRepository;
import com.samin.again.repository_reg.FundraisingEventRepository;
import com.samin.again.repository_reg.RegistrationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DonationService {

    @Autowired
    private DonationRepository donationRepository;

    @Autowired
    private FundraisingEventRepository fundraisingEventRepository;

    @Autowired
    private RegistrationRepo userRepository;

    public Donation createDonation(DonationDTO donationDTO) {
        // Ensure txnId is unique
        if (donationRepository.existsById(donationDTO.getTxnId())) {
            throw new IllegalArgumentException("Transaction ID already exists!");
        }

        // Find the fundraising event
        Optional<FundraisingEvent> fundraisingEvent = fundraisingEventRepository.findById(donationDTO.getFundraisingEventId());
        if (fundraisingEvent.isEmpty()) {
            throw new IllegalArgumentException("Fundraising Event not found!");
        }

        // Find the user
        Optional<User> user = userRepository.findById(donationDTO.getUserId());
        if (user.isEmpty()) {
            throw new IllegalArgumentException("User not found!");
        }

        // Create and save donation
        Donation donation = new Donation();
        donation.setTxnId(donationDTO.getTxnId());
        donation.setAmount(donationDTO.getAmount());
        donation.setTime(LocalDateTime.now());
        donation.setFundraisingEvent(fundraisingEvent.get());
        donation.setUser(user.get());

        return donationRepository.save(donation);
    }

    public List<Donation> getDonationsByEvent(Long eventId) {
        return donationRepository.findByFundraisingEventEventId(eventId);
    }

    public List<Donation> getDonationsByUser(Long userId) {
        return donationRepository.findByUserNid(userId);
    }
    public double getTotalDonationsByEvent(Long eventId) {
        List<Donation> donations = donationRepository.findByFundraisingEventEventId(eventId);
        return donations.stream().mapToDouble(Donation::getAmount).sum();
    }
    public List<DonationDTO> getDonationsGroupedByDate() {
        List<Object[]> results = donationRepository.findDonationsGroupedByDate();
        return results.stream()
                .map(result -> {
                    DonationDTO dto = new DonationDTO();
                    // Cast result[0] to java.sql.Date and convert it to LocalDate
                    dto.setTime(((java.sql.Date) result[0]).toLocalDate().atStartOfDay());
                    dto.setAmount((Double) result[1]);
                    return dto;
                })
                .collect(Collectors.toList());
    }


}
