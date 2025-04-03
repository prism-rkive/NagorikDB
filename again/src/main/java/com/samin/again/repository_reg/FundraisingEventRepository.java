package com.samin.again.repository_reg;

import com.samin.again.entity.FundraisingEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FundraisingEventRepository extends JpaRepository<FundraisingEvent, Long> {
    List<FundraisingEvent> findByAdminId(Long adminId); // Find events by admin
    
    @Query("SELECT DATE(d.time) AS donationDate, SUM(d.amount) AS totalAmount " +
            "FROM Donation d WHERE d.fundraisingEvent.eventId = :eventId " +
            "GROUP BY DATE(d.time) ORDER BY donationDate ASC")
    List<Object[]> findDonationsByDate(@Param("eventId") Long eventId);


    @Query("SELECT d.fundraisingEvent.eventId AS eventId, SUM(d.amount) AS totalAmount " +
            "FROM Donation d GROUP BY d.fundraisingEvent.eventId")
    List<Object[]> findTotalDonationsByEvent();



    @Query("SELECT d.user.nid AS userId, SUM(d.amount) AS totalAmount " +
            "FROM Donation d WHERE d.fundraisingEvent.eventId = :eventId " +
            "GROUP BY d.user.nid ORDER BY totalAmount DESC")
    List<Object[]> findTopDonorsForEvent(@Param("eventId") Long eventId);

    @Query("SELECT d.fundraisingEvent.eventId AS eventId, SUM(d.amount) AS totalAmount " +
            "FROM Donation d WHERE d.fundraisingEvent.admin.id = :adminId " +
            "GROUP BY d.fundraisingEvent.eventId")
    List<Object[]> findTotalDonationsByAdminEvents(@Param("adminId") Long adminId);




    @Query("SELECT d.user.nid AS userId, SUM(d.amount) AS totalAmount " +
            "FROM Donation d GROUP BY d.user.nid ORDER BY totalAmount DESC")
    List<Object[]> findDonationsByUser();





}
