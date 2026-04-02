package com.vimal.tournax.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "games")
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "game_name", nullable = false, unique = true)
    private String gameName;

    @Column(name = "genre", nullable = false)
    private String genre;

    @OneToMany(mappedBy = "game")
    @JsonIgnore
    private List<Tournament> tournaments = new ArrayList<>();

    public Game() {}

    public Game(Long id, String gameName, String genre) {
        this.id = id;
        this.gameName = gameName;
        this.genre = genre;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getGameName() { return gameName; }
    public void setGameName(String gameName) { this.gameName = gameName; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public List<Tournament> getTournaments() { return tournaments; }
    public void setTournaments(List<Tournament> tournaments) { this.tournaments = tournaments; }
}
