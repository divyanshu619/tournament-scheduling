package com.mediaocean.tournament_scheduling.controllers;

import com.mediaocean.tournament_scheduling.dto.TournamentRequestDto;
import com.mediaocean.tournament_scheduling.exception.CustomException;
import com.mediaocean.tournament_scheduling.models.Tournament;
import com.mediaocean.tournament_scheduling.services.TournamentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TournamentControllerTest {

    @Mock
    TournamentService tournamentService;
    @InjectMocks
    TournamentController tournamentController;

    TournamentRequestDto tournamentRequestDto;

    @Before
    public void setUp() throws Exception {
        tournamentRequestDto = mock(TournamentRequestDto.class);
    }

    @Test
    public void testCreateTournament() throws CustomException {
        when(tournamentService.createTournament(tournamentRequestDto)).thenReturn(mock(Tournament.class));
        ResponseEntity<Tournament> tournament = tournamentController.createTournament(tournamentRequestDto);
        assertNotNull(tournament);
        assertEquals(HttpStatus.OK, tournament.getStatusCode());
    }

    @Test
    public void testCreateTournamentException() throws CustomException {
        when(tournamentService.createTournament(tournamentRequestDto)).thenThrow(new CustomException("Error message"));
        assertThrows(ResponseStatusException.class, () -> {
            ResponseEntity<Tournament> tournament = tournamentController.createTournament(tournamentRequestDto);
        });
    }
}