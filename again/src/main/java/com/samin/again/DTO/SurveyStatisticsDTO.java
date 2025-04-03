package com.samin.again.DTO;

public class SurveyStatisticsDTO {
    private String question;
    private Double yesPercentage;
    private Double noPercentage;
    private Long adminId;

    // Constructors
    public SurveyStatisticsDTO() {}

    public SurveyStatisticsDTO(String question, Double yesPercentage, Double noPercentage, Long adminId) {
        this.question = question;
        this.yesPercentage = yesPercentage;
        this.noPercentage = noPercentage;
        this.adminId = adminId;
    }

    // Getters and setters
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Double getYesPercentage() {
        return yesPercentage;
    }

    public void setYesPercentage(Double yesPercentage) {
        this.yesPercentage = yesPercentage;
    }

    public Double getNoPercentage() {
        return noPercentage;
    }

    public void setNoPercentage(Double noPercentage) {
        this.noPercentage = noPercentage;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }
}
