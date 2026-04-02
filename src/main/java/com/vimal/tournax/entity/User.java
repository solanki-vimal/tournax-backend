package com.vimal.tournax.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username", unique = true, nullable = false)
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @ManyToOne
    @JoinColumn(name = "team_id")
    @JsonBackReference("team-players")
    private Team team;

    public User() {}

    public User(Long id, String username, String password, String role, String email) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Team getTeam() { return team; }
    public void setTeam(Team team) { this.team = team; }

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() { return true; }
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() { return true; }
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() { return true; }
    @JsonIgnore
    @Override
    public boolean isEnabled() { return true; }
}
