package com.vimal.tournax.service;

import com.vimal.tournax.entity.Team;
import java.util.List;

public interface TeamService {
    Team createTeam(Team team, String managerUsername);
    List<Team> getAllTeams();
    Team getTeamById(Long id);
    Team updateTeam(Long id, Team team);
    void deleteTeam(Long id);
    Team addPlayerToTeam(Long teamId, Long playerId);
    Team joinTeam(Long teamId, String username);
    Team leaveTeam(Long teamId, String username);
}
