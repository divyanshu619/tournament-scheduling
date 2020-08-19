package com.mediaocean.tournament_scheduling.services.impl;

import com.mediaocean.tournament_scheduling.dao.TeamDAO;
import com.mediaocean.tournament_scheduling.dto.TeamAdditionRequest;
import com.mediaocean.tournament_scheduling.exception.CustomException;
import com.mediaocean.tournament_scheduling.models.Team;
import com.mediaocean.tournament_scheduling.services.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TeamServiceImpl implements TeamService {

    @Autowired
    TeamDAO teamDAO;
    @Autowired
    Validator validator;

    @Override
    @Transactional
    public List<Team> saveTeams(TeamAdditionRequest request) throws CustomException {
        // Validate Request
        Set<ConstraintViolation<TeamAdditionRequest>> constraintViolations = validator.validate(request);
        if (!constraintViolations.isEmpty()) {
            String validationErrorMessages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(
                    Collectors.joining(":"));
            log.error("Validation errors : " + validationErrorMessages);
            throw new CustomException(validationErrorMessages);
        }
        return teamDAO.saveMultipleTeams(request.getTeamList());
    }
}
