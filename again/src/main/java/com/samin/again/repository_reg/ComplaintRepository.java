package com.samin.again.repository_reg;

import com.samin.again.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint,Long> {
    List<Complaint> findByUserNidOrderByPostedAtDesc(Long nid);
    List<Complaint>findByStatusAndSectorOrderByPostedAtAsc(Cstatus status, String sector);
    @Query("SELECT c.sector, COUNT(c) FROM Complaint c GROUP BY c.sector")
    List<Object[]> countComplaintBySector();
    @Query("SELECT c.department, COUNT(c) FROM Complaint c GROUP BY c.department")
    List<Object[]> countComplaintByDepartment();
    @Query("SELECT COUNT(c) FROM Complaint c WHERE FUNCTION('DATE', c.postedAt) = CURRENT_DATE")
    Integer countComplaintToday();
    @Query("SELECT COUNT(c) FROM Complaint c WHERE c.status = RESOLVED AND FUNCTION('DATE', c.postedAt) = CURRENT_DATE")
    Integer countResolvedToday();

    @Query("SELECT e.department, COUNT(e) AS count " +
            "FROM Complaint e " +
            "WHERE e.sector = :sector " +
            "GROUP BY e.department " )
    List<Object[]> countComplaintByDepartmentInSector(@Param("sector") String sector);
    @Query("SELECT e.status, COUNT(e) AS count " +
            "FROM Complaint e " +
            "WHERE e.sector = :sector " +
            "GROUP BY e.status ")
    List<Object[]> countComplaintByStatusInSector(@Param("sector") String sector);


    @Query("SELECT COUNT(e) FROM Complaint e WHERE e.status = RESOLVED AND e.sector=:sector AND FUNCTION('DATE', e.postedAt) = CURRENT_DATE")
    Integer countResolvedInSectorToday(@Param("sector") String sector);

    @Query("SELECT COUNT(e) FROM Complaint e WHERE  e.sector=:sector AND FUNCTION('DATE', e.postedAt) = CURRENT_DATE")
    Integer countReportedInSectorToday(@Param("sector") String sector);
    @Query("SELECT COUNT(e) FROM Complaint e WHERE e.status = RESOLVED AND e.sector=:sector ")
    Integer countResolvedInSector(@Param("sector") String sector);
    @Query("SELECT COUNT(e) FROM Complaint e WHERE  e.sector=:sector")
    Integer countReportedInSector(@Param("sector") String sector);


}
