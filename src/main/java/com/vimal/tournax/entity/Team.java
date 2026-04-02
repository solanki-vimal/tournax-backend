package com.vimal.tournax.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "team_name", nullable = false, unique = true)
    private String teamName;

    @ManyToOne
    @JoinColumn(name = "manager_id", nullable = false)
    private User manager;

    @OneToMany(mappedBy = "team")
    @JsonIgnore
    private List<User> players = new ArrayList<>();

    @OneToMany(mappedBy = "team1")
    @JsonIgnore
    private List<Match> matchesAsTeam1 = new ArrayList<>();

    @OneToMany(mappedBy = "team2")
    @JsonIgnore
    private List<Match> matchesAsTeam2 = new ArrayList<>();

    public Team() {}

    public Team(Long id, String teamName, User manager) {
        this.id = id;
        this.teamName = teamName;
        this.manager = manager;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTeamName() { return teamName; }
    public void setTeamName(String teamName) { this.teamName = teamName; }

    public User getManager() { return manager; }
    public void setManager(User manager) { this.manager = manager; }

    public List<User> getPlayers() { return players; }
    public void setPlayers(List<User> players) { this.players = players; }

    public List<Match> getMatchesAsTeam1() { return matchesAsTeam1; }
    public void setMatchesAsTeam1(List<Match> matchesAsTeam1) { this.matchesAsTeam1 = matchesAsTeam1; }

    public List<Match> getMatchesAsTeam2() { return matchesAsTeam2; }
    public void setMatchesAsTeam2(List<Match> matchesAsTeam2) { this.matchesAsTeam2 = matchesAsTeam2; }
}

