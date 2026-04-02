package com.vimal.tournax.service;

import com.vimal.tournax.entity.Game;
import com.vimal.tournax.exception.ResourceNotFoundException;
import com.vimal.tournax.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    @Transactional
    public Game createGame(Game game) {
        return gameRepository.save(game);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Game getGameById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Game not found with id: " + id));
    }

    @Override
    @Transactional
    public Game updateGame(Long id, Game game) {
        Game existing = gameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Game not found with id: " + id));
        existing.setGameName(game.getGameName());
        existing.setGenre(game.getGenre());
        return gameRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteGame(Long id) {
        if (!gameRepository.existsById(id)) {
            throw new ResourceNotFoundException("Game not found with id: " + id);
        }
        gameRepository.deleteById(id);
    }
}
