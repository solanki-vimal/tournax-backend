package com.vimal.tournax.controller;

import com.vimal.tournax.entity.Tournament;
import com.vimal.tournax.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

/**
 * Controller class to manage tournaments.
 */
@RestController
@RequestMapping("/tournaments")
@Validated
public class TournamentController {

    private final TournamentService tournamentService;

    @Autowired
    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @PostMapping
    public ResponseEntity<Tournament> createTournament(
            @Valid @RequestBody Tournament tournament,
            @RequestParam Long gameId) {
        tournament.setId(null);
        Tournament created = tournamentService.createTournament(tournament, gameId);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Tournament>> getAllTournaments() {
        return ResponseEntity.ok(tournamentService.getAllTournaments());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tournament> getTournamentById(@PathVariable Long id) {
        return ResponseEntity.ok(tournamentService.getTournamentById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Tournament> updateTournament(
            @PathVariable Long id,
            @Valid @RequestBody Tournament tournament,
            @RequestParam Long gameId) {
        return ResponseEntity.ok(tournamentService.updateTournament(id, tournament, gameId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTournament(@PathVariable Long id) {
        tournamentService.deleteTournament(id);
        return ResponseEntity.noContent().build();
    }
}
