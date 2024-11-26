package com.codecool.codekickfc.dto.matches;

import com.codecool.codekickfc.repository.model.FootballPitch;

import java.time.LocalDateTime;

public record NewMatchDTO(int maxPlayers, double matchFeePerPerson, LocalDateTime matchDate,
                          String matchRules, FootballPitch footballPitch) {
}
