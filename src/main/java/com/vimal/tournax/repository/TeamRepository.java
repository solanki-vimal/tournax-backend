package com.vimal.tournax.repository;

import com.vimal.tournax.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {
}
