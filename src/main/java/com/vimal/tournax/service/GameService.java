package com.vimal.tournax.service;

import com.vimal.tournax.entity.Game;
import java.util.List;

public interface GameService {
    Game createGame(Game game);
    List<Game> getAllGames();
    Game getGameById(Long id);
    Game updateGame(Long id, Game game);
    void deleteGame(Long id);
}
