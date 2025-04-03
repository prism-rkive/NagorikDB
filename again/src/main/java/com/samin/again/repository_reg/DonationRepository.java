package com.samin.again.repository_reg;

import com.samin.again.entity.Donation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DonationRepository extends JpaRepository<com.samin.again.entity.Donation, Long> {
	List<Donation> findByFundraisingEventEventId(Long eventId);

	List<Donation> findByUserNid(Long nid);
	
	@Query("SELECT FUNCTION('DATE', d.time), SUM(d.amount) " +
			"FROM Donation d " +
			"GROUP BY FUNCTION('DATE', d.time) " +
			"ORDER BY FUNCTION('DATE', d.time)")
	List<Object[]> findDonationsGroupedByDate();


}
