package com.mediaocean.tournament_scheduling.controllers;

import com.mediaocean.tournament_scheduling.dto.TournamentRequestDto;
import com.mediaocean.tournament_scheduling.exception.CustomException;
import com.mediaocean.tournament_scheduling.models.Tournament;
import com.mediaocean.tournament_scheduling.services.TournamentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@Slf4j
@RequestMapping(value = "/tournament")
public class TournamentController {

    @Autowired
    TournamentService tournamentService;

    @PostMapping(value = "/create")
    public ResponseEntity<Tournament> createTournament(@RequestBody TournamentRequestDto requestDto) {
        try {
            Tournament tournament = tournamentService.createTournament(requestDto);
            return ResponseEntity.ok(tournament);
        } catch (CustomException e) {
            log.error("Error occurred while creating tournament : ", e);
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,
                    "Error occurred while creating tournament : " + e.getMessage());
        }
    }
}
