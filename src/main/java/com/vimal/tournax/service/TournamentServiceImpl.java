package com.vimal.tournax.service;

import com.vimal.tournax.entity.Game;
import com.vimal.tournax.entity.Tournament;
import com.vimal.tournax.exception.ResourceNotFoundException;
import com.vimal.tournax.repository.GameRepository;
import com.vimal.tournax.repository.TournamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TournamentServiceImpl implements TournamentService {

    private final TournamentRepository tournamentRepository;
    private final GameRepository gameRepository;

    @Autowired
    public TournamentServiceImpl(TournamentRepository tournamentRepository, GameRepository gameRepository) {
        this.tournamentRepository = tournamentRepository;
        this.gameRepository = gameRepository;
    }

    @Override
    @Transactional
    public Tournament createTournament(Tournament tournament, Long gameId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ResourceNotFoundException("Game not found"));
        tournament.setGame(game);
        return tournamentRepository.save(tournament);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Tournament getTournamentById(Long id) {
        return tournamentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tournament not found with id: " + id));
    }

    @Override
    @Transactional
    public Tournament updateTournament(Long id, Tournament tournament, Long gameId) {
        Tournament existing = tournamentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tournament not found with id: " + id));
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new ResourceNotFoundException("Game not found with id: " + gameId));
        existing.setTournamentName(tournament.getTournamentName());
        existing.setStartDate(tournament.getStartDate());
        existing.setEndDate(tournament.getEndDate());
        existing.setGame(game);
        return tournamentRepository.save(existing);
    }

    @Override
    @Transactional
    public void deleteTournament(Long id) {
        if (!tournamentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Tournament not found with id: " + id);
        }
        tournamentRepository.deleteById(id);
    }
}
