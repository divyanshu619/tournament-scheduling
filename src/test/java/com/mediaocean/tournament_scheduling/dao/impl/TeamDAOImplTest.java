package com.mediaocean.tournament_scheduling.dao.impl;

import com.mediaocean.tournament_scheduling.models.Team;
import com.mediaocean.tournament_scheduling.repositories.TeamRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TeamDAOImplTest {

    @Mock
    TeamRepository teamRepository;

    @InjectMocks
    TeamDAOImpl teamDAO;

    List<Team> teamList;
    List<Long> idList;

    @Before
    public void setUp() throws Exception {
        teamList = new ArrayList<>();
        teamList.add(new Team("T1", "H1"));
        teamList.add(new Team("T2", "H2"));
        idList = new ArrayList<>();
        idList.add(1l);
        idList.add(2l);
    }

    @Test
    public void saveMultipleTeams() {
        when(teamRepository.saveAll(teamList)).thenReturn(teamList);
        List<Team> savedTeams = teamDAO.saveMultipleTeams(this.teamList);
        assertNotNull(savedTeams);
        assertEquals(teamList.size(), savedTeams.size());
    }

    @Test
    public void findAllById() {
        when(teamRepository.findAllById(idList)).thenReturn(teamList);
        List<Team> savedTeams = teamDAO.findAllById(this.idList);
        assertNotNull(savedTeams);
        assertEquals(teamList.size(), savedTeams.size());
    }
}