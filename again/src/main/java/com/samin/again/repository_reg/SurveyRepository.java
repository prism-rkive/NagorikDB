package com.samin.again.repository_reg;

import java.time.LocalDateTime;
import java.util.List;

import com.samin.again.entity.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface SurveyRepository  extends JpaRepository<Survey,Long>{

	List<Survey> findByAdminId(Long adminId);
	@Query("SELECT s.admin.id, COUNT(s) FROM Survey s GROUP BY s.admin.id")
	List<Object[]> countSurveysByAdmin();
	@Query("SELECT s FROM Survey s WHERE FUNCTION('DATE', s.startTime) = CURRENT_DATE")
	List<Survey> findSurveysCreatedToday();

	@Query("SELECT s.question, " +
			"COALESCE(SUM(CASE WHEN sp.response = 'Yes' THEN 1 ELSE 0 END) * 100.0 / COUNT(sp), 0) AS yesPercentage, " +
			"COALESCE(SUM(CASE WHEN sp.response = 'No' THEN 1 ELSE 0 END) * 100.0 / COUNT(sp), 0) AS noPercentage, " +
			"s.admin.id AS adminId " +
			"FROM Survey s LEFT JOIN s.participations sp " +
			"GROUP BY s.id, s.question, s.admin.id")
	List<Object[]> getSurveyStatistics();

	@Query("SELECT COUNT(s) FROM Survey s WHERE s.startTime <= :currentTime AND s.endTime > :currentTime")
	long countOngoingSurveys(@Param("currentTime") LocalDateTime currentTime);




}
