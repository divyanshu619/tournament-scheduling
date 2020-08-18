package com.mediaocean.tournament_scheduling.services;

import com.mediaocean.tournament_scheduling.models.KabaddiMatch;
import com.mediaocean.tournament_scheduling.models.Team;

import java.time.ZonedDateTime;
import java.util.List;


public interface ScheduleGenerator {
    public List<KabaddiMatch> generateSchedule(List<Team> teamList, ZonedDateTime startingDate);
}
