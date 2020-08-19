package com.mediaocean.tournament_scheduling.controllers;

import com.mediaocean.tournament_scheduling.dto.TeamAdditionRequest;
import com.mediaocean.tournament_scheduling.exception.CustomException;
import com.mediaocean.tournament_scheduling.models.Team;
import com.mediaocean.tournament_scheduling.services.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "/team")
public class TeamController {

    @Autowired
    TeamService teamService;

    @PostMapping(value = "/add-teams")
    public ResponseEntity<List<Team>> addTeams(@RequestBody TeamAdditionRequest requestDto) {
        try {
            List<Team> tournament = teamService.saveTeams(requestDto);
            return ResponseEntity.ok(tournament);
        } catch (CustomException e) {
            log.error("Error occurred while saving teams : ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error occurred while saving teams : " + e.getMessage());
        } catch (Exception e) {
            log.error("Error occurred while saving teams : ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error occurred while saving teams");
        }
    }
}
