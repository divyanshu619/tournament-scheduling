package com.mediaocean.tournament_scheduling.dto;

import com.mediaocean.tournament_scheduling.models.Team;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.List;


@Getter
@Setter
public class TeamAdditionRequest {
    @Size(min = 1, message = "Atleast one team is required for addition")
    List<@Valid Team> teamList;
}
