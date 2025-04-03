package com.samin.again.entity;

import java.io.Serializable;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class SurveyParticipationId implements Serializable {

    @Column(name = "survey_id")
    private Long surveyId;

    @Column(name = "user_id")
    private Long userId;

    // Default constructor
    public SurveyParticipationId() {}

    // Constructor
    public SurveyParticipationId(Long surveyId, Long userId) {
        this.surveyId = surveyId;
        this.userId = userId;
    }

    // Getters and setters
    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    // Override equals and hashCode for composite key to work properly
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SurveyParticipationId that = (SurveyParticipationId) o;
        return surveyId.equals(that.surveyId) && userId.equals(that.userId);
    }

    @Override
    public int hashCode() {
        return 31 * surveyId.hashCode() + userId.hashCode();
    }
}
