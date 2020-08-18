package com.mediaocean.tournament_scheduling.spring;

import com.mediaocean.tournament_scheduling.dao.TournamentDAO;
import com.mediaocean.tournament_scheduling.models.KabaddiMatch;
import com.mediaocean.tournament_scheduling.models.Team;
import com.mediaocean.tournament_scheduling.models.Tournament;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.HashSet;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DAOTests {

    @Autowired
    TournamentDAO tournamentDAO;

    @Test
    public void testSave() {

        Team teamA = new Team("TEAM A", "Pune");
        Team teamB = new Team("TEAM B", "Delhi");

        KabaddiMatch kabaddiMatch = new KabaddiMatch();
        kabaddiMatch.setTeamA(teamA);
        kabaddiMatch.setTeamB(teamB);
        kabaddiMatch.setLocation("Pune");
        kabaddiMatch.setTimeOfMatch(ZonedDateTime.now());

        Tournament tournament = new Tournament();
        tournament.setTournamentName("Tournament 1");
        tournament.setTeamSet(new HashSet<>(Arrays.asList(teamA, teamB)));
        tournament.setKabaddiMatchList(Arrays.asList(kabaddiMatch));

        Tournament saveTournament = tournamentDAO.saveTournament(tournament);

        Assert.assertNotNull(saveTournament);
    }
}
