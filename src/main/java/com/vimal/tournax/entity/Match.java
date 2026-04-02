package com.vimal.tournax.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "matches")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tournament_id", nullable = false)
    @JsonBackReference("tournament-matches")
    private Tournament tournament;

    @ManyToOne
    @JoinColumn(name = "team1_id", nullable = false)
    private Team team1;

    @ManyToOne
    @JoinColumn(name = "team2_id", nullable = false)
    private Team team2;

    @ManyToOne
    @JoinColumn(name = "winner_team_id")
    private Team winnerTeam;

    @Column(name = "match_date", nullable = false)
    private LocalDate matchDate;

    public Match() {}

    public Match(Long id, Tournament tournament, Team team1, Team team2, Team winnerTeam, LocalDate matchDate) {
        this.id = id;
        this.tournament = tournament;
        this.team1 = team1;
        this.team2 = team2;
        this.winnerTeam = winnerTeam;
        this.matchDate = matchDate;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Tournament getTournament() { return tournament; }
    public void setTournament(Tournament tournament) { this.tournament = tournament; }

    public Team getTeam1() { return team1; }
    public void setTeam1(Team team1) { this.team1 = team1; }

    public Team getTeam2() { return team2; }
    public void setTeam2(Team team2) { this.team2 = team2; }

    public Team getWinnerTeam() { return winnerTeam; }
    public void setWinnerTeam(Team winnerTeam) { this.winnerTeam = winnerTeam; }

    public LocalDate getMatchDate() { return matchDate; }
    public void setMatchDate(LocalDate matchDate) { this.matchDate = matchDate; }
}

