package com.samin.again.DTO;

import java.util.List;

public class RescueDTO {

    private Long teamId;
    private String teamName;
    private List<String> contacts;  // List of contacts

    public RescueDTO(Long teamId, String teamName, List<String> contacts) {
        this.teamId = teamId;
        this.teamName = teamName;
        this.contacts = contacts;
    }
    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public List<String> getContacts() {
        return contacts;
    }

    public void setContacts(List<String> contacts) {
        this.contacts = contacts;
    }
    // Getters and Setters
}

