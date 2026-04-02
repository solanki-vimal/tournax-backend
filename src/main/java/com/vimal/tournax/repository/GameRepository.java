package com.vimal.tournax.repository;

import com.vimal.tournax.entity.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
