package com.mediaocean.tournament_scheduling.controllers;

import com.mediaocean.tournament_scheduling.dto.TeamAdditionRequest;
import com.mediaocean.tournament_scheduling.exception.CustomException;
import com.mediaocean.tournament_scheduling.models.Team;
import com.mediaocean.tournament_scheduling.services.TeamService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TeamControllerTest {

    @Mock
    TeamService teamService;
    @InjectMocks
    TeamController teamController;

    TeamAdditionRequest teamAdditionRequest;

    @Before
    public void setUp() throws Exception {
        teamAdditionRequest = mock(TeamAdditionRequest.class);
    }

    @Test
    public void testAddTeams() throws CustomException {
        when(teamService.saveTeams(teamAdditionRequest)).thenReturn(new ArrayList<>());
        ResponseEntity<List<Team>> listResponseEntity = teamController.addTeams(teamAdditionRequest);
        assertNotNull(listResponseEntity);
        assertEquals(HttpStatus.OK, listResponseEntity.getStatusCode());
    }

    @Test
    public void testAddTeamsCustomException() throws CustomException {
        when(teamService.saveTeams(teamAdditionRequest)).thenThrow(new CustomException("Error message"));
        assertThrows(ResponseStatusException.class, () -> {
            ResponseEntity<List<Team>> listResponseEntity = teamController.addTeams(teamAdditionRequest);
        });
    }

}