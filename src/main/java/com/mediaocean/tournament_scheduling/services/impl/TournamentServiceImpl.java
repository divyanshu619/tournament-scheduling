package com.mediaocean.tournament_scheduling.services.impl;

import com.mediaocean.tournament_scheduling.dao.TeamDAO;
import com.mediaocean.tournament_scheduling.dao.TournamentDAO;
import com.mediaocean.tournament_scheduling.dto.TournamentRequestDto;
import com.mediaocean.tournament_scheduling.exception.CustomException;
import com.mediaocean.tournament_scheduling.models.KabaddiMatch;
import com.mediaocean.tournament_scheduling.models.Team;
import com.mediaocean.tournament_scheduling.models.Tournament;
import com.mediaocean.tournament_scheduling.services.ScheduleGenerator;
import com.mediaocean.tournament_scheduling.services.TournamentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TournamentServiceImpl implements TournamentService {

    @Autowired
    TournamentDAO tournamentDAO;
    @Autowired
    TeamDAO teamDAO;
    @Autowired
    ScheduleGenerator scheduleGenerator;
    @Autowired
    Validator validator;

    @Override
    public Tournament createTournament(TournamentRequestDto tournamentRequest) throws CustomException {
        // Validate Request
        Set<ConstraintViolation<TournamentRequestDto>> constraintViolations = validator.validate(tournamentRequest);
        if (!constraintViolations.isEmpty()) {
            String validationErrorMessages = constraintViolations.stream().map(ConstraintViolation::getMessage).collect(
                    Collectors.joining(":"));
            log.error("Validation errors : " + validationErrorMessages);
            throw new CustomException(validationErrorMessages);
        }
        List<Team> teamList = teamDAO.findAllById(tournamentRequest.getTeamIdList());
        if (teamList.size() != tournamentRequest.getTeamIdList().size()) {
            log.error("Invalid team ids sent in request");
            throw new CustomException("Invalid team ids sent in request");
        }
        ZonedDateTime startDateTime;
        try {
            startDateTime = ZonedDateTime.parse(tournamentRequest.getStartingDate());
        } catch (DateTimeParseException e) {
            log.error("Incorrect date format, format should be like 2007-12-03T10:15:30+01:00[Europe/Paris]");
            throw new CustomException(
                    "Incorrect date format, format should be like 2007-12-03T10:15:30+01:00[Europe/Paris]");
        }

        // Generate schedules
        List<KabaddiMatch> kabaddiMatches = scheduleGenerator.generateSchedule(teamList, startDateTime);

        // All validations complete
        Tournament tournament = new Tournament();
        tournament.setTournamentName(tournamentRequest.getTournamentName());
        tournament.setTeamList(teamList);
        tournament.setStartTime(startDateTime);
        tournament.setKabaddiMatchList(kabaddiMatches);

        return tournamentDAO.saveTournament(tournament);
    }
}
