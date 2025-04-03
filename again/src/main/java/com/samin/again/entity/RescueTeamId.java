package com.samin.again.entity;

import java.io.Serializable;
import java.util.Objects;

public class RescueTeamId implements Serializable {

    private Long team_id;    // Matches RescueTeam's team_id
    private String responseTeamId; // Matches ResponseTeam's id (via @ManyToOne)

    // Default constructor
    public RescueTeamId() {
    }

    // Parameterized constructor
    public RescueTeamId(Long team_id,String responseTeam) {
        this.team_id = team_id;
        this.responseTeamId = responseTeam;
    }

    // Getters, Setters, equals, and hashCode
    public Long getTeam_id() {
        return team_id;
    }

    public void setTeam_id(Long team_id) {
        this.team_id = team_id;
    }

    public String getResponseTeamId() {
        return responseTeamId;
    }

    public void setResponseTeamId(String responseTeam) {
        this.responseTeamId = responseTeam;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RescueTeamId that = (RescueTeamId) o;
        return Objects.equals(team_id, that.team_id) && Objects.equals(responseTeamId, that.responseTeamId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(team_id, responseTeamId);
    }
}

