package com.mediaocean.tournament_scheduling.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
public class TournamentRequestDto {
    @NotEmpty(message = "Tournament name is required")
    private String tournamentName;
    @NotEmpty(message = "Starting date is required")
    private String startingDate;
    @NotNull(message = "Team Id List is required")
    @Size(min = 2, message = "At least 2 teams are required for the tournament")
    private Set<Long> teamIdList;
}
