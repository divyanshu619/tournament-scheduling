package com.mediaocean.tournament_scheduling.repositories;

import com.mediaocean.tournament_scheduling.models.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {
}
