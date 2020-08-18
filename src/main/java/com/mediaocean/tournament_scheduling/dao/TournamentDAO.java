package com.mediaocean.tournament_scheduling.dao;

import com.mediaocean.tournament_scheduling.models.Tournament;

public interface TournamentDAO {
    Tournament saveTournament(Tournament tournament);
}
