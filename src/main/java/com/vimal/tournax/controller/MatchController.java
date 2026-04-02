package com.vimal.tournax.controller;

import com.vimal.tournax.dto.MatchResultDto;
import com.vimal.tournax.entity.Match;
import com.vimal.tournax.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

/**
 * Manages matches within tournaments.
 */
@RestController
@RequestMapping("/matches")
@Validated
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @PostMapping
    public ResponseEntity<Match> scheduleMatch(
            @Valid @RequestBody Match match,
            @RequestParam Long tournamentId,
            @RequestParam Long team1Id,
            @RequestParam Long team2Id) {
        match.setId(null);
        Match scheduled = matchService.scheduleMatch(match, tournamentId, team1Id, team2Id);
        return ResponseEntity.status(HttpStatus.CREATED).body(scheduled);
    }

    @GetMapping
    public ResponseEntity<List<Match>> getAllMatches() {
        return ResponseEntity.ok(matchService.getAllMatches());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Match> getMatchById(@PathVariable Long id) {
        return ResponseEntity.ok(matchService.getMatchById(id));
    }

    @PutMapping("/{id}/result")
    public ResponseEntity<Match> updateMatchResult(
            @PathVariable Long id, 
            @RequestBody MatchResultDto resultDto) {
        Match updated = matchService.updateMatchResult(id, resultDto.getWinnerTeamId());
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMatch(@PathVariable Long id) {
        matchService.deleteMatch(id);
        return ResponseEntity.noContent().build();
    }
}
