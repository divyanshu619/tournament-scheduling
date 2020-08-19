package com.mediaocean.tournament_scheduling.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Team extends BaseEntity {
    @NotEmpty(message = "Team name is required")
    @Column(unique = true, nullable = false)
    private String teamName;
    @NotEmpty(message = "Home location is required")
    @Column(nullable = false)
    private String homeLocation;
    @JsonIgnore
    @ManyToMany(mappedBy = "teamList")
    private Set<Tournament> tournamentSet;

    public Team(String teamName, String homeLocation) {
        this.teamName = teamName;
        this.homeLocation = homeLocation;
    }
}
