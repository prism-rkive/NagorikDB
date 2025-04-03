package com.samin.again.entity;

import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // Total disjoint specialization
public abstract class ResponseTeam {
    @Id
    @Column(name="response_id")
    private String responseId;
    private String resp_password;
    private String team_name;

    @OneToMany(mappedBy = "responseTeam", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ResponseContact> contacts = new HashSet<>();



    // Getters and setters
}

