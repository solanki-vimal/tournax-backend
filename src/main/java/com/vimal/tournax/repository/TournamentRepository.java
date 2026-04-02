package com.vimal.tournax.repository;

import com.vimal.tournax.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {
}
