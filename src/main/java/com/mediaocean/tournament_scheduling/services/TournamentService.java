package com.mediaocean.tournament_scheduling.services;

import com.mediaocean.tournament_scheduling.dto.TournamentRequestDto;
import com.mediaocean.tournament_scheduling.exception.CustomException;
import com.mediaocean.tournament_scheduling.models.Tournament;

public interface TournamentService {

    public Tournament createTournament(TournamentRequestDto tournamentRequest) throws CustomException;
}
