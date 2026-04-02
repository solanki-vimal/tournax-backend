package com.vimal.tournax.service;

import com.vimal.tournax.entity.Team;
import com.vimal.tournax.entity.User;
import com.vimal.tournax.exception.ResourceNotFoundException;
import com.vimal.tournax.repository.TeamRepository;
import com.vimal.tournax.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeamServiceImpl implements TeamService {

    private final TeamRepository teamRepository;
    private final UserRepository userRepository;

    @Autowired
    public TeamServiceImpl(TeamRepository teamRepository, UserRepository userRepository) {
        this.teamRepository = teamRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public Team createTeam(Team team, String managerUsername) {
        User manager = userRepository.findByUsername(managerUsername)
                .orElseThrow(() -> new ResourceNotFoundException("Manager not found"));
        team.setManager(manager);
        return teamRepository.save(team);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Team getTeamById(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with id: " + id));
    }

    @Override
    @Transactional
    public Team updateTeam(Long id, Team team) {
        Team existing = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with id: " + id));
        existing.setTeamName(team.getTeamName());
        return teamRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteTeam(Long id) {
        Team team = teamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found with id: " + id));

        if (!team.getMatchesAsTeam1().isEmpty() || !team.getMatchesAsTeam2().isEmpty()) {
            throw new RuntimeException("Cannot delete team that has already participated in matches.");
        }

        List<User> players = team.getPlayers();
        if (players != null) {
            for (User player : players) {
                player.setTeam(null);
                userRepository.save(player);
            }
        }

        teamRepository.delete(team);
    }

    @Override
    @Transactional
    public Team addPlayerToTeam(Long teamId, Long playerId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));
        User player = userRepository.findById(playerId)
                .orElseThrow(() -> new ResourceNotFoundException("Player not found"));

        if (player.getTeam() != null) {
            throw new RuntimeException("Player is already in a team! They must leave their current team first.");
        }

        player.setTeam(team);
        userRepository.save(player);
        return teamRepository.findById(teamId).get();
    }

    @Override
    @Transactional
    public Team joinTeam(Long teamId, String username) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));
        User player = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Player not found"));

        if (!player.getRole().equals("PLAYER")) {
            throw new RuntimeException("Only users with the PLAYER role can join a team directly!");
        }

        if (player.getTeam() != null) {
            throw new RuntimeException("You are already in a team! Leave your current team first.");
        }

        player.setTeam(team);
        userRepository.save(player);
        return teamRepository.findById(teamId).get();
    }

    @Override
    @Transactional
    public Team leaveTeam(Long teamId, String username) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new ResourceNotFoundException("Team not found"));
        User player = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Player not found"));

        if (player.getTeam() == null || !player.getTeam().getId().equals(teamId)) {
            throw new RuntimeException("You are not a member of this team!");
        }

        player.setTeam(null);
        userRepository.save(player);
        team.getPlayers().remove(player);
        return team;
    }
}
