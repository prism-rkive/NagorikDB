package com.samin.again.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@IdClass(RescueTeamId.class)

public class RescueTeam {

    @Id
    @Column(name = "team_id")
    private Long team_id;

    @Id
    @Column(name = "a1_id") // This column holds the primary key of ResponseTeam (response_id)
    private String responseTeamId; // Foreign key referencing ResponseTeam's response_id

    @ManyToOne
    @JoinColumn(name = "a1_id", referencedColumnName = "response_id", insertable = false, updatable = false)
    @JsonBackReference // Indicates this is the "back" side of the relationship
    private ResponseTeam responseTeam;
    @Column(name="T_name")
    private String Name;
    @Enumerated(EnumType.STRING)
    private Rstatus availability;;



    @OneToMany(mappedBy = "rescueTeam", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Prevents recursion by indicating this is the "managed" side of the relationship

    private Set<RescueTeamContact> rescueContacts;


}


