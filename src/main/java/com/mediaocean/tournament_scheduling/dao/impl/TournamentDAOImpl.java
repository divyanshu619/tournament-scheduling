package com.mediaocean.tournament_scheduling.dao.impl;

import com.mediaocean.tournament_scheduling.dao.TournamentDAO;
import com.mediaocean.tournament_scheduling.models.Tournament;
import com.mediaocean.tournament_scheduling.repositories.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TournamentDAOImpl implements TournamentDAO {

    @Autowired
    TournamentRepository tournamentRepository;

    @Override
    public Tournament saveTournament(Tournament tournament) {
        return tournamentRepository.save(tournament);
    }
}
