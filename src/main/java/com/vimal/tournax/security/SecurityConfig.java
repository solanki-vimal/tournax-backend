package com.vimal.tournax.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/register").permitAll()
                        // Game endpoints
                        .requestMatchers(HttpMethod.POST, "/games").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/games/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/games/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/games/**").authenticated()
                        // Tournament endpoints
                        .requestMatchers(HttpMethod.POST, "/tournaments").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/tournaments/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/tournaments/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/tournaments/**").authenticated()
                        // Match endpoints
                        .requestMatchers(HttpMethod.POST, "/matches").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/matches/{id}/result").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/matches/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/matches/**").authenticated()
                        // Team player management
                        .requestMatchers(HttpMethod.POST, "/teams/{teamId}/join").hasRole("PLAYER")
                        .requestMatchers(HttpMethod.DELETE, "/teams/{teamId}/leave").hasRole("PLAYER")
                        .requestMatchers(HttpMethod.POST, "/teams/{teamId}/players/{playerId}").hasRole("MANAGER")
                        // Team CRUD
                        .requestMatchers(HttpMethod.POST, "/teams").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/teams/**").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/teams/{id}").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.GET, "/teams/**").authenticated()

                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .authenticationProvider(authenticationProvider());

        return http.build();
    }
}
