package com.samin.again.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
@PrimaryKeyJoinColumn(name = "a_id") // Rename the inherited primary key column

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class AreaResponseTeam extends ResponseTeam {

    @Column(unique = true)
    private String resp_district;

    @OneToMany(mappedBy = "responseTeam", cascade = CascadeType.ALL, orphanRemoval=true)
    private List<RescueTeam> rescueTeams;

}
