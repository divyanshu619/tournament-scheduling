package com.mediaocean.tournament_scheduling.dao;

import com.mediaocean.tournament_scheduling.models.Team;

import java.util.List;

public interface TeamDAO {

    public List<Team> saveMultipleTeams(Iterable<Team> teams);

    public List<Team> findAllById(Iterable<Long> ids);

}
