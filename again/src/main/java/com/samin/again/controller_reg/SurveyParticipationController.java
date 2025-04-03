/*package com.example.Survey.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.Survey.model.SurveyParticipation;
import com.example.Survey.service.SurveyParticipationService;

@RestController
@RequestMapping("/api/participations")
public class SurveyParticipationController {
	@Autowired
    private SurveyParticipationService participationService;

    // Submit a participation response
	@PostMapping( consumes = "application/json")
	public ResponseEntity<SurveyParticipation> createParticipation(@RequestBody SurveyParticipation participation) {
	    if (participation.getSurvey() == null || participation.getSurvey().getSurveyId() == null) {
	        throw new IllegalArgumentException("Survey ID is required.");
	    }
	    if (participation.getUser() == null || participation.getUser().getNid() == null) {
	        throw new IllegalArgumentException("User ID is required.");
	    }

	    SurveyParticipation savedParticipation = participationService.saveParticipation(participation);
	    return ResponseEntity.ok(savedParticipation);
	}


    // Get all participations by survey ID
    @GetMapping("/survey/{surveyId}")
    public List<SurveyParticipation> getParticipationsBySurveyId(@PathVariable Long surveyId) {
        return participationService.getParticipationsBySurveyId(surveyId);
    }

    // Get all participations by user ID
    @GetMapping("/user/{userId}")
    public List<SurveyParticipation> getParticipationsByUserId(@PathVariable Long userId) {
        return participationService.getParticipationsByUserId(userId);
        
    }
    //get stat bitch
    @GetMapping("/survey/{surveyId}/statistics")
    public String getSurveyStatistics(@PathVariable Long surveyId) {
        return participationService.getSurveyStatistics(surveyId);
    }

	

}*/
package com.samin.again.controller_reg;

import java.util.List;

import com.samin.again.entity.SurveyParticipation;
import com.samin.again.service.SurveyParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/participations")
public class SurveyParticipationController {

    @Autowired
    private SurveyParticipationService participationService;

    // Submit a participation response
  /*  @PostMapping(consumes = "application/json")
    public ResponseEntity<SurveyParticipation> createParticipation(@RequestBody SurveyParticipation participation) {
        if (participation.getSurvey() == null || participation.getSurvey().getSurveyId() == null) {
            throw new IllegalArgumentException("Survey ID is required.");
        }
        if (participation.getUser() == null || participation.getUser().getNid() == null) {
            throw new IllegalArgumentException("User ID is required.");
        }

        SurveyParticipation savedParticipation = participationService.saveParticipation(participation);
        return ResponseEntity.ok(savedParticipation);
    }*/
    @PostMapping(consumes = "application/json")
    public ResponseEntity<SurveyParticipation> createParticipation(@RequestBody SurveyParticipation participation) {
        if (participation.getId() == null || participation.getId().getSurveyId() == null) {
            throw new IllegalArgumentException("Survey ID is required.");
        }
        if (participation.getId().getUserId() == null) {
            throw new IllegalArgumentException("User ID is required.");
        }

        SurveyParticipation savedParticipation = participationService.saveParticipation(participation);
        return ResponseEntity.ok(savedParticipation);
    }



    // Get all participations by survey ID
    @GetMapping("/survey/{surveyId}")
    public List<SurveyParticipation> getParticipationsBySurveyId(@PathVariable Long surveyId) {
        return participationService.getParticipationsBySurveyId(surveyId);
    }

    // Get all participations by user ID
    @GetMapping("/user/{userId}")
    public List<SurveyParticipation> getParticipationsByUserId(@PathVariable Long userId) {
        return participationService.getParticipationsByUserId(userId);
    }

    // Get statistics for a survey
    @GetMapping("/survey/{surveyId}/statistics")
    public String getSurveyStatistics(@PathVariable Long surveyId) {
        return participationService.getSurveyStatistics(surveyId);
    }



    @GetMapping("/count-today")
    public Integer getParticipationCountToday() {
        return participationService.countParticipationToday();
    }

    @GetMapping("/count-by-survey")
    public List<Object[]> getParticipationCountBySurvey() {
        return participationService.countParticipationBySurvey();
    }

    @GetMapping("/count-by-user")
    public List<Object[]> getParticipationCountByUser() {
        return participationService.countParticipationByUser();
    }
    @GetMapping("/count-over-time")
    public List<Object[]> getParticipationCountOverTime() {
        return participationService.countParticipationOverTime();
    }

}

