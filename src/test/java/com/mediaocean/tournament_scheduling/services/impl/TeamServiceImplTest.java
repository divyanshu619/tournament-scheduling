package com.mediaocean.tournament_scheduling.services.impl;

import com.mediaocean.tournament_scheduling.dao.TeamDAO;
import com.mediaocean.tournament_scheduling.dto.TeamAdditionRequest;
import com.mediaocean.tournament_scheduling.exception.CustomException;
import com.mediaocean.tournament_scheduling.models.Team;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.Validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TeamServiceImplTest {

    @Mock
    TeamDAO teamDAO;
    @Mock
    Validator validator;

    @InjectMocks
    TeamServiceImpl teamService;

    TeamAdditionRequest teamAdditionRequest;

    @Before
    public void setUp() throws Exception {
        teamAdditionRequest = mock(TeamAdditionRequest.class);
    }

    @Test
    public void saveTeams() throws CustomException {
        when(validator.validate(teamAdditionRequest)).thenReturn(new HashSet<>());
        when(teamDAO.saveMultipleTeams(any())).thenReturn(new ArrayList<>());

        List<Team> teamList = teamService.saveTeams(teamAdditionRequest);
        assertNotNull(teamList);
    }
}