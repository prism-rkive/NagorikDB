package com.samin.again.repository_reg;


import com.samin.again.entity.Emergency;
import com.samin.again.entity.Status;
import com.samin.again.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface EmergencyRepository extends JpaRepository<Emergency, Long> {
    // Fetch all emergencies for a specific user, sorted by creation time (latest first),can be given find by user
    List<Emergency> findByUserNidOrderByTimepostedDesc(Long nid);
    List<Emergency>findByEstatusAndDistrictOrderByTimepostedAsc(Status status, String district);
    @Query("SELECT e.district, COUNT(e) FROM Emergency e GROUP BY e.district")
    List<Object[]> countEmergenciesByArea();
    @Query("SELECT e.category, COUNT(e) FROM Emergency e GROUP BY e.category")
    List<Object[]> countEmergenciesByCategory();
    @Query("SELECT COUNT(e) FROM Emergency e WHERE FUNCTION('DATE', e.timeposted) = CURRENT_DATE")
    Integer countEmergenciesToday();
    @Query("SELECT COUNT(e) FROM Emergency e WHERE e.estatus = RESCUED AND FUNCTION('DATE', e.timeposted) = CURRENT_DATE")
    Integer countRescuedToday();

    @Query("SELECT e.category , COUNT(e) AS count " +
            "FROM Emergency e " +
            "WHERE e.district = :district " +
            "GROUP BY e.category " )
    List<Object[]> countEmergenciesByCategoryInDistrict(@Param("district") String district);
    @Query("SELECT e.estatus, COUNT(e) AS count " +
            "FROM Emergency e " +
            "WHERE e.district = :district " +
            "GROUP BY e.estatus")
    List<Object[]> countEmergenciesByEstatusInDistrict(@Param("district") String district);


    @Query("SELECT COUNT(e) FROM Emergency e WHERE e.estatus = RESCUED AND e.district=:district AND FUNCTION('DATE', e.timeposted) = CURRENT_DATE")
    Integer countRescuedInDistrictToday(@Param("district") String district);

    @Query("SELECT COUNT(e) FROM Emergency e WHERE  e.district=:district AND FUNCTION('DATE', e.timeposted) = CURRENT_DATE")
    Integer countReportedInDistrictToday(@Param("district") String district);
    @Query("SELECT COUNT(e) FROM Emergency e WHERE e.estatus = RESCUED AND e.district=:district ")
    Integer countRescuedInDistrict(@Param("district") String district);
    @Query("SELECT COUNT(e) FROM Emergency e WHERE  e.district=:district ")
    Integer countReportedInDistrict(@Param("district") String district);






}

