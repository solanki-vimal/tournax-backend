package com.vimal.tournax.dto;

public class MatchResultDto {
    private Long winnerTeamId;

    public MatchResultDto() {}

    public MatchResultDto(Long winnerTeamId) {
        this.winnerTeamId = winnerTeamId;
    }

    public Long getWinnerTeamId() { return winnerTeamId; }
    public void setWinnerTeamId(Long winnerTeamId) { this.winnerTeamId = winnerTeamId; }
}
