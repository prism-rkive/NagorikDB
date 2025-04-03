package com.samin.again.entity;

import java.util.List;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity

public class Admin {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String adminPassword;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Survey> getSurveys() {
		return surveys;
	}

	public void setSurveys(List<Survey> surveys) {
		this.surveys = surveys;
	}

	@Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "admin")
   // @JsonBackReference
   // @JsonIgnoreProperties("admin") 
    private List<Survey> surveys;
    @OneToMany(mappedBy = "admin")  // One admin can have many fundraising events
    private List<FundraisingEvent> fundraisingEvents;
    public List<FundraisingEvent> getFundraisingEvents() {
        return fundraisingEvents;
    }

    public void setFundraisingEvents(List<FundraisingEvent> fundraisingEvents) {
        this.fundraisingEvents = fundraisingEvents;
    }

}
