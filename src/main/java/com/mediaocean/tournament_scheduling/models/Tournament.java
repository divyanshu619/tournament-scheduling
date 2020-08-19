package com.mediaocean.tournament_scheduling.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Tournament extends BaseEntity {
    @Column(nullable = false)
    private String tournamentName;
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "tournament_teams",
            joinColumns = @JoinColumn(name = "tournament_id"),
            inverseJoinColumns = @JoinColumn(name = "team_id")
    )
    private List<Team> teamList;
    private ZonedDateTime startTime;
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "tournament_id")
    private List<KabaddiMatch> kabaddiMatchList;
}
