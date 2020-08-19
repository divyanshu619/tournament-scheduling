package com.mediaocean.tournament_scheduling.services.impl;

import com.mediaocean.tournament_scheduling.models.KabaddiMatch;
import com.mediaocean.tournament_scheduling.models.Team;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class ScheduleGeneratorImplTest {

    @InjectMocks
    ScheduleGeneratorImpl scheduleGenerator;

    List<Team> teamList;

    @Before
    public void setUp() throws Exception {
        teamList = new ArrayList<>();

    }

    @Test
    public void generateScheduleEven() {
        for (int i = 0; i < 4; i++) {
            teamList.add(new Team("Team " + (i + 1), "Home " + (i + 1)));
        }
        List<KabaddiMatch> kabaddiMatches = scheduleGenerator.generateSchedule(teamList, ZonedDateTime.now());
        assertNotNull(kabaddiMatches);
        checkEachTeamHasPlayedBothHomeAndAwayMatch(teamList, kabaddiMatches);
        checkConsecutiveDaysTeams(kabaddiMatches);
    }

    @Test
    public void generateScheduleOdd() {
        for (int i = 0; i < 5; i++) {
            teamList.add(new Team("Team " + (i + 1), "Home " + (i + 1)));
        }
        List<KabaddiMatch> kabaddiMatches = scheduleGenerator.generateSchedule(teamList, ZonedDateTime.now());
        assertNotNull(kabaddiMatches);
        checkEachTeamHasPlayedBothHomeAndAwayMatch(teamList, kabaddiMatches);
        checkConsecutiveDaysTeams(kabaddiMatches);
    }

    private void checkEachTeamHasPlayedBothHomeAndAwayMatch(List<Team> teamList, List<KabaddiMatch> kabaddiMatches) {
        for (Team team : teamList) {
            Set<Team> homeMatches = new HashSet<>();
            Set<Team> awayMatches = new HashSet<>();
            for (KabaddiMatch match : kabaddiMatches) {
                if (match.getTeamA().getTeamName().equals(team.getTeamName())) {
                    homeMatches.add(match.getTeamB());
                }
                if (match.getTeamB().getTeamName().equals(team.getTeamName())) {
                    awayMatches.add(match.getTeamA());
                }
            }
            assertEquals(teamList.size() - 1, homeMatches.size());
            assertEquals(teamList.size() - 1, awayMatches.size());
            assertFalse(homeMatches.contains(team));
            assertFalse(awayMatches.contains(team));
            assertTrue(teamList.containsAll(homeMatches));
            assertTrue(teamList.containsAll(awayMatches));
        }
    }

    private void checkConsecutiveDaysTeams(List<KabaddiMatch> kabaddiMatches) {
        Set<String> previousTeamList = new HashSet<>();
        ZonedDateTime previousMatchDate1 = null;
        ZonedDateTime previousMatchDate2 = null;
        for (KabaddiMatch match : kabaddiMatches) {
            if (previousTeamList.isEmpty()) {
                // Initial Data
                previousMatchDate1 = match.getTimeOfMatch();
            } else if (previousMatchDate1.getDayOfMonth() == match.getTimeOfMatch().getDayOfMonth()) {
                // Same date match
                previousMatchDate2 = match.getTimeOfMatch();
            } else if (previousMatchDate1.getDayOfMonth() == match.getTimeOfMatch().plusDays(1).getDayOfMonth() ||
                    (previousMatchDate2 != null &&
                            previousMatchDate2.getDayOfMonth() == match.getTimeOfMatch().plusDays(1).getDayOfMonth())) {
                // Next date match
                assertFalse(previousTeamList.contains(match.getTeamA().getTeamName()));
                assertFalse(previousTeamList.contains(match.getTeamB().getTeamName()));

                previousTeamList = new HashSet<>();

                previousMatchDate1 = match.getTimeOfMatch();
                previousMatchDate2 = null;
            } else {
                previousTeamList = new HashSet<>();
                previousMatchDate1 = match.getTimeOfMatch();
                previousMatchDate2 = null;
            }

            previousTeamList.add(match.getTeamA().getTeamName());
            previousTeamList.add(match.getTeamB().getTeamName());
        }
    }
}