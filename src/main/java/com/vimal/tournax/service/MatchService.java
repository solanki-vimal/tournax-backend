package com.vimal.tournax.service;

import com.vimal.tournax.entity.Match;
import java.util.List;

public interface MatchService {
    Match scheduleMatch(Match match, Long tournamentId, Long team1Id, Long team2Id);
    List<Match> getAllMatches();
    Match getMatchById(Long id);
    Match updateMatchResult(Long matchId, Long winnerTeamId);
    void deleteMatch(Long id);
}
