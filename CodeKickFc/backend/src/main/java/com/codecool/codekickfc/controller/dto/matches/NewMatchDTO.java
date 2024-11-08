package com.codecool.codekickfc.controller.dto.matches;

import com.codecool.codekickfc.dao.model.pitches.FootballPitch;

import java.time.LocalDateTime;

public record NewMatchDTO(int maxPlayers, double matchFeePerPerson, LocalDateTime matchDate,
                          String matchRules, FootballPitch footballPitch) {
}
