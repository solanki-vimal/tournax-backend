package com.vimal.tournax.controller;

import com.vimal.tournax.entity.Team;
import com.vimal.tournax.service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

/**
 * This controller is for managing Teams and players.
 */
@RestController
@RequestMapping("/teams")
@Validated
public class TeamController {

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @PostMapping
    public ResponseEntity<Team> createTeam(@Valid @RequestBody Team team) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String managerUsername = auth.getName();
        
        team.setId(null);
        Team created = teamService.createTeam(team, managerUsername);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Team>> getAllTeams() {
        return ResponseEntity.ok(teamService.getAllTeams());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable Long id) {
        return ResponseEntity.ok(teamService.getTeamById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable Long id, @Valid @RequestBody Team team) {
        return ResponseEntity.ok(teamService.updateTeam(id, team));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id) {
        teamService.deleteTeam(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{teamId}/players/{playerId}")
    public ResponseEntity<Team> addPlayerToTeam(@PathVariable Long teamId, @PathVariable Long playerId) {
        Team updatedTeam = teamService.addPlayerToTeam(teamId, playerId);
        return ResponseEntity.ok(updatedTeam);
    }

    @PostMapping("/{teamId}/join")
    public ResponseEntity<Team> joinTeam(@PathVariable Long teamId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Team updated = teamService.joinTeam(teamId, username);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{teamId}/leave")
    public ResponseEntity<Team> leaveTeam(@PathVariable Long teamId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Team updated = teamService.leaveTeam(teamId, username);
        return ResponseEntity.ok(updated);
    }
}
