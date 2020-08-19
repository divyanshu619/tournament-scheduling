package com.mediaocean.tournament_scheduling.services.impl;

import com.mediaocean.tournament_scheduling.dao.TeamDAO;
import com.mediaocean.tournament_scheduling.dao.TournamentDAO;
import com.mediaocean.tournament_scheduling.dto.TournamentRequestDto;
import com.mediaocean.tournament_scheduling.exception.CustomException;
import com.mediaocean.tournament_scheduling.models.Tournament;
import com.mediaocean.tournament_scheduling.services.ScheduleGenerator;
import org.hibernate.validator.engine.HibernateConstraintViolation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TournamentServiceImplTest {

    @Mock
    TournamentDAO tournamentDAO;
    @Mock
    TeamDAO teamDAO;
    @Mock
    ScheduleGenerator scheduleGenerator;
    @Mock
    Validator validator;
    @InjectMocks
    TournamentServiceImpl tournamentService;

    TournamentRequestDto tournamentRequestDto;

    @Before
    public void setUp() throws Exception {
        tournamentRequestDto = new TournamentRequestDto();
        tournamentRequestDto.setStartingDate("2007-12-03T10:15:30+01:00[Europe/Paris]");
        tournamentRequestDto.setTeamIdList(new HashSet<>());
        tournamentRequestDto.setTournamentName("tournament_name");
    }

    @Test
    public void createTournament() throws CustomException {
        when(validator.validate(tournamentRequestDto)).thenReturn(new HashSet<>());
        when(teamDAO.findAllById(any())).thenReturn(new ArrayList<>());
        when(scheduleGenerator.generateSchedule(any(), any())).thenReturn(new ArrayList<>());
        when(tournamentDAO.saveTournament(any())).thenReturn(mock(Tournament.class));

        Tournament tournament = tournamentService.createTournament(tournamentRequestDto);
        assertNotNull(tournament);
    }

    @Test
    public void createTournamentWithInvalidDateFormat() {
        when(validator.validate(tournamentRequestDto)).thenReturn(new HashSet<>());
        when(teamDAO.findAllById(any())).thenReturn(new ArrayList<>());

        tournamentRequestDto.setStartingDate("Invaliddate");
        assertThrows(CustomException.class, () -> {
            Tournament tournament = tournamentService.createTournament(tournamentRequestDto);
        });
    }

    @Test
    public void createTournamentWithInvalidIdList() {
        when(validator.validate(tournamentRequestDto)).thenReturn(new HashSet<>());
        when(teamDAO.findAllById(any())).thenReturn(new ArrayList<>());

        Set<Long> idSet =new HashSet<>();
        idSet.add(1l);
        tournamentRequestDto.setTeamIdList(idSet);
        assertThrows(CustomException.class, () -> {
            Tournament tournament = tournamentService.createTournament(tournamentRequestDto);
        });
    }
}