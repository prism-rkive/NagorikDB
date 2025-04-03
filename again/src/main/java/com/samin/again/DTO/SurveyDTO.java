package com.samin.again.DTO;

import java.time.LocalDateTime;

public class SurveyDTO {
    private Long surveyId;
    private String question;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    // Constructor
    public SurveyDTO(Long surveyId, String question, LocalDateTime startTime, LocalDateTime endTime) {
        this.surveyId = surveyId;
        this.question = question;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters and Setters
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
}
