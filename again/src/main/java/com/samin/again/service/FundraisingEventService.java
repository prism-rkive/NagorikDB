package com.samin.again.service;


import com.samin.again.DTO.FundraisingEventDTO;
import com.samin.again.entity.Admin;
import com.samin.again.entity.FundraisingEvent;
import com.samin.again.repository_reg.AdminRepository;
import com.samin.again.repository_reg.FundraisingEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FundraisingEventService {

    @Autowired
    private FundraisingEventRepository fundraisingEventRepository;

    @Autowired
    private AdminRepository adminRepository;

    // Create or Update Fundraising Event
    public FundraisingEvent saveFundraisingEvent(FundraisingEventDTO fundraisingEventDTO) {
        Optional<Admin> admin = adminRepository.findById(fundraisingEventDTO.getAdminId());
        if (admin.isEmpty()) {
            throw new IllegalArgumentException("Admin not found!");
        }

        FundraisingEvent fundraisingEvent = new FundraisingEvent();
        if (fundraisingEventDTO.getEventId() != null) {
            fundraisingEvent = fundraisingEventRepository.findById(fundraisingEventDTO.getEventId())
                    .orElseThrow(() -> new IllegalArgumentException("Event not found!"));
        }

        fundraisingEvent.setCause(fundraisingEventDTO.getCause());
        fundraisingEvent.setStartTime(fundraisingEventDTO.getStartTime());
        fundraisingEvent.setEndTime(fundraisingEventDTO.getEndTime());
        fundraisingEvent.setAdmin(admin.get());

        return fundraisingEventRepository.save(fundraisingEvent);
    }

    // Get all events
    public List<FundraisingEvent> getAllEvents() {
        return fundraisingEventRepository.findAll();
    }

    // Get events by admin
    public List<FundraisingEvent> getEventsByAdmin(Long adminId) {
        return fundraisingEventRepository.findByAdminId(adminId);
    }

    // Delete an event
    public void deleteEvent(Long eventId) {
        if (!fundraisingEventRepository.existsById(eventId)) {
            throw new IllegalArgumentException("Event not found!");
        }
        fundraisingEventRepository.deleteById(eventId);
    }
    public FundraisingEvent getEventById(Long eventId) {
        return fundraisingEventRepository.findById(eventId).orElse(null);
    }

    public List<Object[]> getDonationsByDate(Long eventId) {
        return fundraisingEventRepository.findDonationsByDate(eventId);
    }

    public List<Object[]> getTotalDonationsByEvent() {
        return fundraisingEventRepository.findTotalDonationsByEvent();
    }

    public List<Object[]> getTopDonorsForEvent(Long eventId) {
        return fundraisingEventRepository.findTopDonorsForEvent(eventId);
    }

    public List<Object[]> getTotalDonationsByAdminEvents(Long adminId) {
        return fundraisingEventRepository.findTotalDonationsByAdminEvents(adminId);
    }

    public List<Object[]> getDonationsByUser() {
        return fundraisingEventRepository.findDonationsByUser();
    }





}
