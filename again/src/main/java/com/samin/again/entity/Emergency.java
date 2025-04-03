package com.samin.again.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

    public class Emergency {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long case_ID;
        @Enumerated(EnumType.STRING)
        private Status estatus;
        private String category;
        @Column (name="time_posted")
        private LocalDateTime timeposted;
        private Integer no_affected;
        private String district;
        private Integer postalCode;
        private String road;
        private String house;
        private String thana;
        private String description;


    @ManyToOne
    @JoinColumn(name ="nid4",nullable = false)

    private User user;

       /* @ManyToOne
        @JoinColumn(name = "rescue_team_id", nullable = true)
        private RescueTeam assignedTeam;*/

    public User getUser() {
        return user;
    }



    public void setUser(User user) {
        this.user = user;
    }
}



        // Getters and setters



