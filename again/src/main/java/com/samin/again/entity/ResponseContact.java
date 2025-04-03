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
@IdClass(ResponseContactId.class)
@Entity
public class ResponseContact {
    @Id
    private String contact;

    @Id
    @Column(name = "response_id1", insertable = false, updatable = false) // Maps to the ResponseTeam's ID
    private String responseId;

    @ManyToOne
    @JoinColumn(name = "response_id1", nullable = false) // Foreign key mapping to ResponseTeam
    private ResponseTeam responseTeam;
}

