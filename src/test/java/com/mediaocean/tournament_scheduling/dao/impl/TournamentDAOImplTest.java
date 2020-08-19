package com.mediaocean.tournament_scheduling.dao.impl;

import com.mediaocean.tournament_scheduling.models.Tournament;
import com.mediaocean.tournament_scheduling.repositories.TournamentRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TournamentDAOImplTest {

    @Mock
    TournamentRepository tournamentRepository;
    @InjectMocks
    TournamentDAOImpl tournamentDAO;

    Tournament tournament;

    @Before
    public void setUp() throws Exception {
        tournament = mock(Tournament.class);
    }

    @Test
    public void testSaveTournament() {
        when(tournamentRepository.save(tournament)).thenReturn(tournament);
        Tournament savedTournament = tournamentDAO.saveTournament(tournament);
        assertNotNull(savedTournament);
    }
}