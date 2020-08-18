package com.mediaocean.tournament_scheduling.repositories;

import com.mediaocean.tournament_scheduling.models.KabaddiMatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<KabaddiMatch, Long> {
}
