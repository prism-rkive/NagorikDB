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

public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long complaint_id;
    @ManyToOne
    @JoinColumn(name ="nid3",nullable = false)
    private User user;
    private String contact_no;
    private String sector;
    private String department;
    @Enumerated(EnumType.STRING)
    private Cstatus status;
    @Lob
    @Column(columnDefinition = "LONGBLOB") // Suitable for images or large files
    private byte[] attachment;
    private LocalDateTime postedAt;
    private String details;









}
