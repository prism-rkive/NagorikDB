package com.samin.again.controller_reg;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.samin.again.DTO.SurveyDTO;
import com.samin.again.DTO.SurveyStatisticsDTO;
import com.samin.again.entity.Survey;
import com.samin.again.service.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/surveys")
public class SurveyController {

    @Autowired
    private SurveyService surveyService;

    // Create a new survey
    @PostMapping
    public Survey createSurvey(@RequestBody Survey survey) {
        return surveyService.saveSurvey(survey);
    }

    // Get all surveys (mapped to SurveyDTO)
    @GetMapping
    public List<SurveyDTO> getAllSurveys() {
        List<Survey> surveys = surveyService.getAllSurveys();
        return surveys.stream()
                .map(survey -> new SurveyDTO(
                        survey.getSurveyId(),
                        survey.getQuestion(),
                        survey.getStartTime(),
                        survey.getEndTime())) // Include adminId only
                .collect(Collectors.toList());
    }

    // Get a survey by ID (mapped to SurveyDTO)
    @GetMapping("/{id}")
    public Optional<SurveyDTO> getSurveyById(@PathVariable Long id) {
        Optional<Survey> survey = surveyService.getSurveyById(id);
        return survey.map(s -> new SurveyDTO(
                s.getSurveyId(),
                s.getQuestion(),
                s.getStartTime(),
                s.getEndTime())); // Include adminId only
    }

    // Delete a survey by ID
    @DeleteMapping("/{id}")
    public void deleteSurvey(@PathVariable Long id) {
        surveyService.deleteSurvey(id);
    }

    // Get surveys by admin ID (mapped to SurveyDTO)
    @GetMapping("/admin/{adminId}")
    public List<SurveyDTO> getSurveysByAdmin(@PathVariable Long adminId) {
        List<Survey> surveys = surveyService.getSurveysByAdmin(adminId);
        return surveys.stream()
                .map(survey -> new SurveyDTO(
                        survey.getSurveyId(),
                        survey.getQuestion(),
                        survey.getStartTime(),
                        survey.getEndTime())) // Include adminId only
                .collect(Collectors.toList());
    }


    @GetMapping("/created-today")
    public List<Survey> getSurveysCreatedToday() {
        return surveyService.findSurveysCreatedToday();
    }

    @GetMapping("/count-by-admin")
    public List<Object[]> countSurveysByAdmin() {
        return surveyService.countSurveysByAdmin();
    }

    @GetMapping("/statistics")
    public List<SurveyStatisticsDTO> getSurveyStatistics() {
        return surveyService.getSurveyStatistics();
    }

    @GetMapping("/ongoing-surveys-count")
    public long getOngoingSurveysCount() {
        return surveyService.countOngoingSurveys();
    }




}
