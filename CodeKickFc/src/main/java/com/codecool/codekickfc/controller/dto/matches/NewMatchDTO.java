package com.codecool.codekickfc.controller.dto.matches;

import java.sql.Array;
import java.time.LocalDateTime;

public record NewMatchDTO(Array subscribed_players_id, double match_fee_per_players, int field_id, LocalDateTime match_date) {
}
