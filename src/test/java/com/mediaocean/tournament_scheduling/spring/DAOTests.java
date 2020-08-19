package com.mediaocean.tournament_scheduling.spring;

import com.mediaocean.tournament_scheduling.dao.TournamentDAO;
import com.mediaocean.tournament_scheduling.models.KabaddiMatch;
import com.mediaocean.tournament_scheduling.models.Team;
import com.mediaocean.tournament_scheduling.models.Tournament;
import com.mediaocean.tournament_scheduling.services.ScheduleGenerator;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
public class DAOTests {

    Logger log = LoggerFactory.getLogger(DAOTests.class);

    @Autowired
    TournamentDAO tournamentDAO;

    @Autowired
    ScheduleGenerator scheduleGenerator;

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
        tournament.setTeamList(Arrays.asList(teamA, teamB));
        tournament.setKabaddiMatchList(Collections.singletonList(kabaddiMatch));

        Tournament saveTournament = tournamentDAO.saveTournament(tournament);

        Assert.assertNotNull(saveTournament);
    }

    @Test
    public void scheduleGenTest() {
        List<Team> teamList = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            teamList.add(new Team(String.valueOf(i + 1), "Home " + (i + 1)));
        }

        List<KabaddiMatch> kabaddiMatches = scheduleGenerator.generateSchedule(teamList, ZonedDateTime.now());
        log.info("TESTS COMPLETE");
    }
}
