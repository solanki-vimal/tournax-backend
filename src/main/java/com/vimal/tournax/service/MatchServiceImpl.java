package com.vimal.tournax.service;

import com.vimal.tournax.entity.Match;
import com.vimal.tournax.entity.Team;
import com.vimal.tournax.entity.Tournament;
import com.vimal.tournax.exception.ResourceNotFoundException;
import com.vimal.tournax.repository.MatchRepository;
import com.vimal.tournax.repository.TeamRepository;
import com.vimal.tournax.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MatchServiceImpl implements MatchService {

    private final MatchRepository matchRepository;
    private final TournamentRepository tournamentRepository;
    private final TeamRepository teamRepository;

    @Autowired
    public MatchServiceImpl(MatchRepository matchRepository, TournamentRepository tournamentRepository, TeamRepository teamRepository) {
        this.matchRepository = matchRepository;
        this.tournamentRepository = tournamentRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    @Transactional
    public Match scheduleMatch(Match match, Long tournamentId, Long team1Id, Long team2Id) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new ResourceNotFoundException("Tournament not found"));
        Team team1 = teamRepository.findById(team1Id)
                .orElseThrow(() -> new ResourceNotFoundException("Team 1 not found"));
        Team team2 = teamRepository.findById(team2Id)
                .orElseThrow(() -> new ResourceNotFoundException("Team 2 not found"));

        if (team1.getId().equals(team2.getId())) {
            throw new IllegalArgumentException("Team 1 and Team 2 cannot be the same team");
        }

        match.setTournament(tournament);
        match.setTeam1(team1);
        match.setTeam2(team2);
        return matchRepository.save(match);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Match getMatchById(Long id) {
        return matchRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found with id: " + id));
    }

    @Override
    @Transactional
    public Match updateMatchResult(Long matchId, Long winnerTeamId) {
        Match match = matchRepository.findById(matchId)
                .orElseThrow(() -> new ResourceNotFoundException("Match not found"));
        Team winner = teamRepository.findById(winnerTeamId)
                .orElseThrow(() -> new ResourceNotFoundException("Winner team not found"));
        
        match.setWinnerTeam(winner);
        return matchRepository.save(match);
    }

    @Override
    @Transactional
    public void deleteMatch(Long id) {
        if (!matchRepository.existsById(id)) {
            throw new ResourceNotFoundException("Match not found with id: " + id);
        }
        matchRepository.deleteById(id);
    }
}
