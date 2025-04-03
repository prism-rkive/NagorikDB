package com.samin.again.service;

import com.samin.again.entity.*;
import com.samin.again.entity.SectorResponseTeam;
import com.samin.again.repository_reg.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class SectorResponseService {
    @Autowired
    private ComplaintRepository complaintRepository;
    @Autowired
    private ResponseRepository responseRepository;
    @Autowired
    private SectorResponseRepo sectorRepository;


    public Optional<SectorResponseTeam> authenticateUser(String a_id, String password) {
        return (sectorRepository.findById(a_id)
                .filter(responseTeam ->responseTeam.getResp_password().equals(password)));
    }
    public Optional<SectorResponseTeam> getATeamById(String a_id) {

        return sectorRepository.findById(a_id);
    }

    public List<Complaint> getAssignedComplaintBySector(String sector) {
        System.out.println("Fetching user with district: ");
        return complaintRepository.findByStatusAndSectorOrderByPostedAtAsc(Cstatus.ASSIGNED,sector);
    }
    public List<Complaint> getProcessingComplaintBySector(String sector) {
        System.out.println("Fetching user with district: ");
        return complaintRepository.findByStatusAndSectorOrderByPostedAtAsc(Cstatus.PROCESSING,sector);
    }
    public List<Complaint> getUnassignedComplaintsBySector(String sector) {
        System.out.println("Fetching user with district: ");
        return complaintRepository.findByStatusAndSectorOrderByPostedAtAsc(Cstatus.SUBMITTED,sector);
    }
    public List<Complaint> getResolvedComplaintBySector(String sector) {
        System.out.println("Fetching user with district: ");
        return complaintRepository.findByStatusAndSectorOrderByPostedAtAsc(Cstatus.RESOLVED,sector);
    }
}
