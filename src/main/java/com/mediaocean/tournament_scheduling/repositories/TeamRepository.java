package com.mediaocean.tournament_scheduling.repositories;

import com.mediaocean.tournament_scheduling.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
}
