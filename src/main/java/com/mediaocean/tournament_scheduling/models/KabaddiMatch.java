package com.mediaocean.tournament_scheduling.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
public class KabaddiMatch extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Team teamA;
    @ManyToOne(fetch = FetchType.LAZY)
    private Team teamB;
    private ZonedDateTime timeOfMatch;
    private String location;
}
