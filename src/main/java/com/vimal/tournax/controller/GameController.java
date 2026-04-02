package com.vimal.tournax.controller;

import com.vimal.tournax.entity.Game;
import com.vimal.tournax.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;

/**
 * Handles all Game related operations.
 */
@RestController
@RequestMapping("/games")
@Validated
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @PostMapping
    public ResponseEntity<Game> createGame(@Valid @RequestBody Game game) {
        game.setId(null);
        Game created = gameService.createGame(game);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<Game>> getAllGames() {
        return ResponseEntity.ok(gameService.getAllGames());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> getGameById(@PathVariable Long id) {
        return ResponseEntity.ok(gameService.getGameById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Game> updateGame(@PathVariable Long id, @Valid @RequestBody Game game) {
        return ResponseEntity.ok(gameService.updateGame(id, game));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long id) {
        gameService.deleteGame(id);
        return ResponseEntity.noContent().build();
    }
}
