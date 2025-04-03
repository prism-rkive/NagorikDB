package com.samin.again.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;


@Entity
@Table(name = "survey")
public class Survey {
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long surveyId;

	    @Column(nullable = false)
	    private String question;

	    private LocalDateTime startTime;

	    private LocalDateTime endTime;
	    public Survey()
	    {
	    	
	    }

	    @ManyToOne
	    @JoinColumn(name = "admin_id", nullable = false)
	   // @JsonManagedReference 
	   // @JsonIgnore 
	  //  @JsonIgnoreProperties("surveys")
	    private Admin admin;

	    @OneToMany(mappedBy = "survey")
	 //   @JsonBackReference 
	    private List<SurveyParticipation> participations;

		public Long getSurveyId() {
			return surveyId;
		}

		public void setSurveyId(Long surveyId) {
			this.surveyId = surveyId;
		}

		public String getQuestion() {
			return question;
		}

		public void setQuestion(String question) {
			this.question = question;
		}

		public LocalDateTime getStartTime() {
			return startTime;
		}

		public void setStartTime(LocalDateTime startTime) {
			this.startTime = startTime;
		}

		public LocalDateTime getEndTime() {
			return endTime;
		}

		public void setEndTime(LocalDateTime endTime) {
			this.endTime = endTime;
		}

		public Admin getAdmin() {
			return admin;
		}

		public void setAdmin(Admin admin) {
			this.admin = admin;
		}

		public List<SurveyParticipation> getParticipations() {
			return participations;
		}

		public void setParticipations(List<SurveyParticipation> participations) {
			this.participations = participations;
		}
}
