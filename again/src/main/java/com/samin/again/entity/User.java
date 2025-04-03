package com.samin.again.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter


public class User {

    @Id
    private Long nid;
    private String name;

    private String password;



    public Long getNID() {
        return nid;
    }


    public void setNID(Long NID){this.nid=NID;}

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Emergency> emergencies;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Complaint> complaints;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<SurveyParticipation> participations;

    @OneToMany(mappedBy = "user")  // One user can have many donations
    @JsonIgnore
    private List<Donation> donations;

    public List<Emergency> getEmergencies() {
        return emergencies;
    }

    public void setEmergencies(List<Emergency>emergencies) {
        this.emergencies = emergencies;
    }



}