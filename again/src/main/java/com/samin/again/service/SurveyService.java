package com.samin.again.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.samin.again.DTO.SurveyStatisticsDTO;
import com.samin.again.entity.Admin;
import com.samin.again.entity.Survey;
import com.samin.again.repository_reg.AdminRepository;
import com.samin.again.repository_reg.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class SurveyService {
	@Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private AdminRepository adminRepository;  // You need to inject AdminRepository

    // Create or update a survey
    public Survey saveSurvey(Survey survey) {
        // Fetch the admin based on the adminId
        Admin admin = adminRepository.findById(survey.getAdmin().getId())
                .orElseThrow(() -> new RuntimeException("Admin not found"));

        // Set the admin to the survey
        survey.setAdmin(admin);

        // Save and return the survey
        return surveyRepository.save(survey);
    }

    // Get all surveys
    public List<Survey> getAllSurveys() {
        return surveyRepository.findAll();
    }

    // Get a survey by ID
    public Optional<Survey> getSurveyById(Long id) {
        return surveyRepository.findById(id);
    }

    // Delete a survey by ID
    public void deleteSurvey(Long id) {
        surveyRepository.deleteById(id);
    }

    // Custom logic: Get all surveys by an admin ID
    public List<Survey> getSurveysByAdmin(Long adminId) {
        return surveyRepository.findByAdminId(adminId);


    }

    public List<Survey> findSurveysCreatedToday() {
        return surveyRepository.findSurveysCreatedToday();
    }

    public List<Object[]> countSurveysByAdmin() {
        return surveyRepository.countSurveysByAdmin();
    }
    public List<SurveyStatisticsDTO> getSurveyStatistics() {
        List<Object[]> results = surveyRepository.getSurveyStatistics();

        // Map raw query results to DTO
        return results.stream()
                .map(row -> new SurveyStatisticsDTO(
                        (String) row[0],     // Question
                        (Double) row[1],     // Yes percentage
                        (Double) row[2],     // No percentage
                        (Long) row[3]        // Admin ID
                ))
                .collect(Collectors.toList());
    }

    public long countOngoingSurveys() {
        return surveyRepository.countOngoingSurveys(LocalDateTime.now());
    }



}
