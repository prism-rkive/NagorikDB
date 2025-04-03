package com.samin.again.service;

import com.samin.again.entity.*;
import com.samin.again.repository_reg.AreaResponseRepository;
import com.samin.again.repository_reg.EmergencyRepository;
import com.samin.again.repository_reg.RescueRepository;
import com.samin.again.repository_reg.ResponseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.geom.Area;
import java.util.List;
import java.util.Optional;

@Service
public class AreaResponseService {
    @Autowired
    private EmergencyRepository emergencyRepository;
    @Autowired
    private ResponseRepository responseRepository;
    @Autowired
    private AreaResponseRepository areaRepository;


    public Optional<AreaResponseTeam> authenticateUser(String a_id, String password) {
        return (areaRepository.findById(a_id)
                .filter(responseTeam ->responseTeam.getResp_password().equals(password)));
    }
    public Optional<AreaResponseTeam> getATeamById(String a_id) {

        return areaRepository.findById(a_id);
    }

    public List<Emergency> getUnassignedEmergenciesByDistrict(String district) {
        System.out.println("Fetching user with district: ");
        return emergencyRepository.findByEstatusAndDistrictOrderByTimepostedAsc(Status.SUBMITTED,district);
    }
    public List<Emergency> getAssignedEmergenciesByDistrict(String district) {
        System.out.println("Fetching user with district: ");
        return emergencyRepository.findByEstatusAndDistrictOrderByTimepostedAsc(Status.ASSIGNED,district);
    }
    public List<Emergency> getRescuedEmergenciesByDistrict(String district) {
        System.out.println("Fetching user with district: ");
        return emergencyRepository.findByEstatusAndDistrictOrderByTimepostedAsc(Status.RESCUED,district);
    }
    public List<Emergency> getDelayedEmergenciesByDistrict(String district) {
        System.out.println("Fetching user with district: ");
        return emergencyRepository.findByEstatusAndDistrictOrderByTimepostedAsc(Status.DELAYED,district);
    }
    public List<Emergency> getResolvedEmergenciesByDistrict(String district) {
        System.out.println("Fetching user with district: ");
        return emergencyRepository.findByEstatusAndDistrictOrderByTimepostedAsc(Status.RESOLVED,district);
    }
    public List<RescueTeam> getMyTeams(String response_id){
        AreaResponseTeam areaTeam = areaRepository.findById(response_id)
                .orElseThrow(() -> new RuntimeException("ResponseTeam not found"));
        return (areaTeam).getRescueTeams();
    }

}

