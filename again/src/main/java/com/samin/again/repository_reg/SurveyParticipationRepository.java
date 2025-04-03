/*package com.example.Survey.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Survey.model.SurveyParticipation;

public interface SurveyParticipationRepository extends JpaRepository<SurveyParticipation, Long> {

	 List<SurveyParticipation> findBySurvey_SurveyId(Long surveyId);

	 List<SurveyParticipation> findByUser_Nid(Long nid);


}*/
package com.samin.again.repository_reg;

import java.util.List;

import com.samin.again.entity.SurveyParticipation;
import com.samin.again.entity.SurveyParticipationId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface SurveyParticipationRepository extends JpaRepository<SurveyParticipation, SurveyParticipationId> {

    List<SurveyParticipation> findBySurvey_SurveyId(Long surveyId);

    List<SurveyParticipation> findByUser_Nid(Long userId);




    @Query("SELECT sp.survey.surveyId, COUNT(sp) FROM SurveyParticipation sp GROUP BY sp.survey.surveyId")
    List<Object[]> countParticipationBySurvey();

    @Query("SELECT COUNT(sp) FROM SurveyParticipation sp WHERE FUNCTION('DATE', sp.submissionTime) = CURRENT_DATE")
    Integer countParticipationToday();


    @Query("SELECT sp.user.nid, COUNT(sp) FROM SurveyParticipation sp GROUP BY sp.user.nid")
    List<Object[]> countParticipationByUser();

    @Query("SELECT FUNCTION('DATE', sp.submissionTime) AS participationDate, COUNT(sp) AS count " +
            "FROM SurveyParticipation sp " +
            "GROUP BY FUNCTION('DATE', sp.submissionTime) " +
            "ORDER BY participationDate")
    List<Object[]> countParticipationOverTime();



}

