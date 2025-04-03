package com.samin.again.DTO;

import java.time.LocalDateTime;

public class FundraisingEventDTO {
    private Long eventId; // For updates
    private String cause;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long adminId;
    private double totalDonated;

    public double getTotalDonated() {
		return totalDonated;
	}

	public void setTotalDonated(double totalDonated) {
		this.totalDonated = totalDonated;
	}
    // Getters and Setters
    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }
}
