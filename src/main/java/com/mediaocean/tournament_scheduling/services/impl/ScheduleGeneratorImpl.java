package com.mediaocean.tournament_scheduling.services.impl;

import com.mediaocean.tournament_scheduling.models.KabaddiMatch;
import com.mediaocean.tournament_scheduling.models.Team;
import com.mediaocean.tournament_scheduling.services.ScheduleGenerator;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ScheduleGeneratorImpl implements ScheduleGenerator {

    private int maxMatchesInDay = 2;

    @Override
    public List<KabaddiMatch> generateSchedule(List<Team> teamList, ZonedDateTime startingDate) {
        List<KabaddiMatch> kabaddiMatches = new ArrayList<>();
        boolean dummyTeam = false;
        int numTeams = teamList.size();
        if (numTeams % 2 != 0) {
            numTeams++;
            dummyTeam = true;
        }

        int[][] teamIndexes = new int[2][numTeams / 2];
        fillTeamIndexes(teamIndexes, numTeams, dummyTeam);


        for (int i = 0; i < numTeams - 1; i++) {
            addKabaddiMatch(teamIndexes, teamList, numTeams, kabaddiMatches);
            rotate(teamIndexes, numTeams);
        }
        kabaddiMatches.addAll(generateAwayMatches(kabaddiMatches));
        return addDatesToSchedule(kabaddiMatches, startingDate);
    }

    private void fillTeamIndexes(int[][] teamIndexes, int numberOfTeams, boolean dummyTeam) {
        int half = numberOfTeams / 2;
        for (int index = 0; index < half; index++) {
            teamIndexes[0][index] = index + 1;
        }

        for (int i = half - 1, j = half + 1; i >= 0; i--, j++) {
            if (dummyTeam && i == 0) {
                teamIndexes[1][i] = 0;
            } else {
                teamIndexes[1][i] = j;
            }
        }
    }

    private void rotate(int[][] teamIndexes, int numberOfTeams) {
        int half = numberOfTeams / 2;
        int elementToMoveFromRow1 = teamIndexes[0][half - 1];
        int elementToMoveFromRow2 = teamIndexes[1][0];
        // Rotate row 1
        for (int i = 1; i < half - 1; i++) {
            teamIndexes[0][i + 1] = teamIndexes[0][i];
        }
        // Rotate row 2
        for (int i = 1; i < half; i++) {
            teamIndexes[1][i - 1] = teamIndexes[1][i];
        }
        // Insert the value to be moved
        teamIndexes[0][1] = elementToMoveFromRow2;
        teamIndexes[1][half - 1] = elementToMoveFromRow1;
    }

    private void addKabaddiMatch(int[][] teamIndexes, List<Team> teamList, int numTeams,
                                 List<KabaddiMatch> kabaddiMatches) {
        for (int i = 0; i < numTeams / 2; i++) {
            if (teamIndexes[0][i] != 0 && teamIndexes[1][i] != 0) {
                KabaddiMatch kabaddiMatch = new KabaddiMatch();
                kabaddiMatch.setTeamA(teamList.get(teamIndexes[0][i] - 1));
                kabaddiMatch.setTeamB(teamList.get(teamIndexes[1][i] - 1));
                kabaddiMatch.setLocation(teamList.get(teamIndexes[0][i] - 1).getHomeLocation());
                kabaddiMatches.add(kabaddiMatch);
            }
        }
    }

    private List<KabaddiMatch> generateAwayMatches(List<KabaddiMatch> homeMatches) {
        List<KabaddiMatch> awayMatches = new ArrayList<>();
        for (KabaddiMatch homeMatch : homeMatches) {
            KabaddiMatch awayMatch = new KabaddiMatch();
            awayMatch.setTeamA(homeMatch.getTeamB());
            awayMatch.setTeamB(homeMatch.getTeamA());
            awayMatch.setLocation(homeMatch.getTeamB().getHomeLocation());
            awayMatches.add(awayMatch);
        }
        return awayMatches;
    }

    private List<KabaddiMatch> addDatesToSchedule(List<KabaddiMatch> kabaddiMatchList, ZonedDateTime startingDate) {
        List<KabaddiMatch> finalList = new ArrayList<>();
        ZonedDateTime eventDate = startingDate;
        int listSize = kabaddiMatchList.size();
        while (finalList.size() != listSize) {
            KabaddiMatch match = kabaddiMatchList.stream().findFirst().get();
            if (finalList.size() < maxMatchesInDay) {
                match.setTimeOfMatch(eventDate);
                finalList.add(match);
                kabaddiMatchList.remove(match);
            } else {
                List<KabaddiMatch> lastDayMatches = getLastDayKabaddiMatches(finalList, maxMatchesInDay);
                List<KabaddiMatch> nonConflictingMatches =
                        kabaddiMatchList.stream().filter(match1 -> !checkIfTeamsMatchInList(match1, lastDayMatches))
                                .collect(
                                        Collectors.toList());

                if (nonConflictingMatches.size() < maxMatchesInDay) {
                    eventDate = eventDate.plusDays(2);
                } else {
                    eventDate = eventDate.plusDays(1);
                }
                ZonedDateTime zonedDateTime = eventDate;
                List<KabaddiMatch> collect =
                        kabaddiMatchList.stream().limit(maxMatchesInDay).collect(Collectors.toList());
                collect.forEach(match1 -> {
                    match1.setTimeOfMatch(zonedDateTime);
                });
                finalList.addAll(collect);
                kabaddiMatchList.removeAll(collect);
            }
        }
        return finalList;
    }

    private boolean checkIfTeamsMatchInList(KabaddiMatch match1, List<KabaddiMatch> matchList) {
        for (KabaddiMatch kabaddiMatch : matchList) {
            if (checkIfTeamsMatch(match1, kabaddiMatch)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkIfTeamsMatch(KabaddiMatch match1, KabaddiMatch match2) {
        if (match1.getTeamA().getTeamName().equals(match2.getTeamA().getTeamName()) ||
                match1.getTeamA().getTeamName().equals(match2.getTeamB().getTeamName())) {
            return true;
        }
        return match1.getTeamB().getTeamName().equals(match2.getTeamA().getTeamName()) ||
                match1.getTeamB().getTeamName().equals(match2.getTeamB().getTeamName());
    }

    private List<KabaddiMatch> getLastDayKabaddiMatches(List<KabaddiMatch> kabaddiMatchList, int maxMatchesInDay) {
        List<KabaddiMatch> lastDayMatches = new ArrayList<>();
        int indexToAddFrom;
        int matchListSize = kabaddiMatchList.size();
        if (matchListSize <= maxMatchesInDay) {
            return kabaddiMatchList;
        } else {
            if (matchListSize % maxMatchesInDay == 0) {
                indexToAddFrom = maxMatchesInDay;
            } else {
                indexToAddFrom = matchListSize % maxMatchesInDay;
            }
            for (int i = matchListSize - indexToAddFrom; i < matchListSize; i++) {
                lastDayMatches.add(kabaddiMatchList.get(i));
            }
        }
        return lastDayMatches;
    }
}
