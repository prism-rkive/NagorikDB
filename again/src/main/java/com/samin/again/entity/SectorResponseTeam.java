package com.samin.again.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@PrimaryKeyJoinColumn(name = "s_id") // Rename the inherited primary key column

public class SectorResponseTeam extends ResponseTeam {

    @Column(unique = true)
    private String resp_sector;


}


