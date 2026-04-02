package com.vimal.tournax.service;

import com.vimal.tournax.entity.Tournament;
import java.util.List;

public interface TournamentService {
    Tournament createTournament(Tournament tournament, Long gameId);
    List<Tournament> getAllTournaments();
    Tournament getTournamentById(Long id);
    Tournament updateTournament(Long id, Tournament tournament, Long gameId);
    void deleteTournament(Long id);
}
