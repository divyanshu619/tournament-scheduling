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
@Setter
@Getter
public class ScheduleGeneratorImpl implements ScheduleGenerator {

    private int maxMatchesInDay = 2;

    @Override
    public List<KabaddiMatch> generateSchedule(List<Team> teamList, ZonedDateTime startingDate) {


        int numTeams = teamList.size();
        int[] teamNumbers;
        if (teamList.size() % 2 == 0) {
            teamNumbers = new int[numTeams - 1];
        } else {
            teamNumbers = new int[numTeams];
        }

        int numDays = (numTeams - 1);
        int halfSize = numTeams / 2;
        for (int i = 2; i <= numTeams; i++) {
            teamNumbers[i - 2] = i;
        }

        int teamsSize = teamNumbers.length;

        List<KabaddiMatch> kabaddiMatches = new ArrayList<>();

        for (int day = 0; day < numDays; day++) {
            log.info("Day" + (day + 1));

            int teamIdx = day % teamsSize;

            log.info(teamNumbers[teamIdx] + " vs 1");
//            if(teamNumbers[teamIdx] != 0) {
            KabaddiMatch kabaddiMatchWithTeam1 = new KabaddiMatch();
            kabaddiMatchWithTeam1.setTeamA(teamList.get(0));
            kabaddiMatchWithTeam1.setTeamB(teamList.get(teamNumbers[teamIdx] - 1));
            kabaddiMatchWithTeam1.setLocation(teamList.get(0).getHomeLocation());
            kabaddiMatches.add(kabaddiMatchWithTeam1);
//            }

            for (int idx = 1; idx < halfSize; idx++) {
                int firstTeam = (day + idx) % teamsSize;
                int secondTeam = (day + teamsSize - idx) % teamsSize;
                log.info(teamNumbers[firstTeam] + " vs " + teamNumbers[secondTeam]);
//                if(teamNumbers[firstTeam] != 0 && teamNumbers[secondTeam] != 0) {
                KabaddiMatch kabaddiMatch = new KabaddiMatch();
                kabaddiMatch.setTeamA(teamList.get(teamNumbers[firstTeam] - 1));
                kabaddiMatch.setTeamB(teamList.get(teamNumbers[secondTeam] - 1));
                kabaddiMatch.setLocation(teamList.get(teamNumbers[firstTeam] - 1).getHomeLocation());
                kabaddiMatches.add(kabaddiMatch);
//                }
            }
        }
        kabaddiMatches.addAll(generateAwayMatches(kabaddiMatches));
        return addDatesToSchedule(kabaddiMatches, startingDate);
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
