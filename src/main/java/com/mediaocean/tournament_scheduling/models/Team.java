package com.mediaocean.tournament_scheduling.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Team extends BaseEntity {
    @Column(unique = true, nullable = false)
    private String teamName;
    @Column(nullable = false)
    private String homeLocation;
    @ManyToMany(mappedBy = "teamSet")
    private Set<Tournament> tournamentSet;

    public Team(String teamName, String homeLocation) {
        this.teamName = teamName;
        this.homeLocation = homeLocation;
    }
}
