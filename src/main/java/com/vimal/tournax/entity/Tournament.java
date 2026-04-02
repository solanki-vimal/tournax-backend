package com.vimal.tournax.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tournaments")
public class Tournament {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "tournament_name", nullable = false)
    private String tournamentName;

    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    @JsonBackReference("game-tournaments")
    private Game game;

    @OneToMany(mappedBy = "tournament")
    @JsonIgnore
    private List<Match> matches = new ArrayList<>();

    public Tournament() {}

    public Tournament(Long id, String tournamentName, LocalDate startDate, LocalDate endDate, Game game) {
        this.id = id;
        this.tournamentName = tournamentName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.game = game;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTournamentName() { return tournamentName; }
    public void setTournamentName(String tournamentName) { this.tournamentName = tournamentName; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public Game getGame() { return game; }
    public void setGame(Game game) { this.game = game; }

    public List<Match> getMatches() { return matches; }
    public void setMatches(List<Match> matches) { this.matches = matches; }
}

