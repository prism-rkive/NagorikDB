package com.samin.again.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@IdClass(RescueTeamContactId.class) // Specify the ID class
@Entity
public class RescueTeamContact {

    @Id
    private String r_contact;

    @Id
    @Column(name = "team_id2")
    private Long team_id2;

    @Id
    @Column(name = "a2_id")
    private String a2_id;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "team_id2", referencedColumnName = "team_id", insertable = false, updatable = false),
            @JoinColumn(name = "a2_id", referencedColumnName = "a1_id", insertable = false, updatable = false)
    })
    @JsonBackReference  // Prevents recursion by indicating this is the "back" side of the relationship
    private RescueTeam rescueTeam; // Foreign key mapping

    // Getters and Setters
}


