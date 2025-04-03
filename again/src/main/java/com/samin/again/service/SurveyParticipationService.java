/*package com.example.Survey.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Survey.model.Survey;
import com.example.Survey.model.SurveyParticipation;
import com.example.Survey.model.User;
import com.example.Survey.repository.SurveyParticipationRepository;
import com.example.Survey.repository.SurveyRepository;
import com.example.Survey.repository.UserRepository;

@Service

public class SurveyParticipationService {
	 @Autowired
	    private SurveyParticipationRepository surveyParticipationRepository;
	 @Autowired
	    private UserRepository userRepository;
	 @Autowired
	    private SurveyRepository surveyRepository;

	    // Create or update a participation record
	 public SurveyParticipation saveParticipation(SurveyParticipation participation) {
	        // Fetch existing Survey
	        Survey survey = surveyRepository.findById(participation.getSurvey().getSurveyId())
	                .orElseThrow(() -> new IllegalArgumentException("Invalid Survey ID"));

	        // Fetch existing User
	        User user = userRepository.findById(participation.getUser().getNid())
	                .orElseThrow(() -> new IllegalArgumentException("Invalid User ID"));

	        // Associate existing entities
	        participation.setSurvey(survey);
	        participation.setUser(user);

	        // Save participation
	        return surveyParticipationRepository.save(participation);
	    }
	   
	    // Get all participation records
	    public List<SurveyParticipation> getAllParticipations() {
	        return surveyParticipationRepository.findAll();
	    }

	    // Get participation by ID
	    public Optional<SurveyParticipation> getParticipationById(Long id) {
	        return surveyParticipationRepository.findById(id);
	    }

	    // Get participations for a specific survey
	    public List<SurveyParticipation> getParticipationsBySurveyId(Long surveyId) {
	        return surveyParticipationRepository.findBySurvey_SurveyId(surveyId);
	    }

	    // Get statistics for a survey (percentage of "Yes" and "No")
	 // Get statistics for a survey (percentage of "Yes" and "No")
	    public String getSurveyStatistics(Long surveyId) {
	        List<SurveyParticipation> participations = getParticipationsBySurveyId(surveyId);
	        long total = participations.size();

	        // Handle case where there are no participations
	        if (total == 0) {
	            return "No responses yet for this survey.";
	        }

	        long yesCount = participations.stream()
	                                      .filter(p -> p.getResponse().equalsIgnoreCase("yes"))
	                                      .count();
	        long noCount = total - yesCount;

	        long yesPercentage = (yesCount * 100) / total;
	        long noPercentage = (noCount * 100) / total;

	        return "Yes: " + yesPercentage + "%, No: " + noPercentage + "%";
	    }


	    // Delete a participation record by ID
	    public void deleteParticipation(Long id) {
	        surveyParticipationRepository.deleteById(id);
	    }
	    public List<SurveyParticipation> getParticipationsByUserId(Long userId) {
	        return surveyParticipationRepository.findByUser_Nid(userId);
	    }

}*/
package com.samin.again.service;

import java.util.List;
import java.util.Optional;

import com.samin.again.entity.Survey;
import com.samin.again.entity.SurveyParticipation;
import com.samin.again.entity.SurveyParticipationId;
import com.samin.again.entity.User;
import com.samin.again.repository_reg.RegistrationRepo;
import com.samin.again.repository_reg.SurveyParticipationRepository;
import com.samin.again.repository_reg.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class SurveyParticipationService {

    @Autowired
    private SurveyParticipationRepository surveyParticipationRepository;

    @Autowired
    private RegistrationRepo userRepository;

    @Autowired
    private SurveyRepository surveyRepository;

    // Create or update a participation record
    public SurveyParticipation saveParticipation(SurveyParticipation participation) {
        SurveyParticipationId participationId = participation.getId();

        // Fetch existing Survey
        Survey survey = surveyRepository.findById(participationId.getSurveyId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Survey ID"));

        // Fetch existing User
        User user = userRepository.findById(participationId.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid User ID"));

        // Associate existing entities
        participation.setSurvey(survey);
        participation.setUser(user);

        // Save participation
        return surveyParticipationRepository.save(participation);
    }

    // Get all participation records
    public List<SurveyParticipation> getAllParticipations() {
        return surveyParticipationRepository.findAll();
    }

    // Get participation by ID
    public Optional<SurveyParticipation> getParticipationById(SurveyParticipationId id) {
        return surveyParticipationRepository.findById(id);
    }

    // Get participations for a specific survey
    public List<SurveyParticipation> getParticipationsBySurveyId(Long surveyId) {
        return surveyParticipationRepository.findBySurvey_SurveyId(surveyId);
    }

    // Get statistics for a survey (percentage of "Yes" and "No")
    public String getSurveyStatistics(Long surveyId) {
        List<SurveyParticipation> participations = getParticipationsBySurveyId(surveyId);
        long total = participations.size();

        if (total == 0) {
            return "No responses yet for this survey.";
        }

        long yesCount = participations.stream()
                .filter(p -> p.getResponse().equalsIgnoreCase("yes"))
                .count();
        long noCount = total - yesCount;

        long yesPercentage = (yesCount * 100) / total;
        long noPercentage = (noCount * 100) / total;

        return "Yes: " + yesPercentage + "%, No: " + noPercentage + "%";
    }


    // Delete a participation record by ID
    public void deleteParticipation(SurveyParticipationId id) {
        surveyParticipationRepository.deleteById(id);
    }

    public List<SurveyParticipation> getParticipationsByUserId(Long userId) {
        return surveyParticipationRepository.findByUser_Nid(userId);

    }


    public Integer countParticipationToday() {
        return surveyParticipationRepository.countParticipationToday();
    }

    public List<Object[]> countParticipationBySurvey() {
        return surveyParticipationRepository.countParticipationBySurvey();
    }

    public List<Object[]> countParticipationByUser() {
        return surveyParticipationRepository.countParticipationByUser();
    }

    public List<Object[]> countParticipationOverTime() {
        return surveyParticipationRepository.countParticipationOverTime();
    }
}

