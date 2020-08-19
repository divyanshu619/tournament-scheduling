package com.mediaocean.tournament_scheduling.services;

import com.mediaocean.tournament_scheduling.dto.TeamAdditionRequest;
import com.mediaocean.tournament_scheduling.exception.CustomException;
import com.mediaocean.tournament_scheduling.models.Team;

import java.util.List;

public interface TeamService {

    List<Team> saveTeams(TeamAdditionRequest request) throws CustomException;
}
