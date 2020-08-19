package com.mediaocean.tournament_scheduling.dao.impl;

import com.mediaocean.tournament_scheduling.dao.TeamDAO;
import com.mediaocean.tournament_scheduling.models.Team;
import com.mediaocean.tournament_scheduling.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TeamDAOImpl implements TeamDAO {

    @Autowired
    TeamRepository teamRepository;

    @Override
    public List<Team> saveMultipleTeams(Iterable<Team> teams) {
        return teamRepository.saveAll(teams);
    }

    @Override
    public List<Team> findAllById(Iterable<Long> ids) {
        return teamRepository.findAllById(ids);
    }
}
