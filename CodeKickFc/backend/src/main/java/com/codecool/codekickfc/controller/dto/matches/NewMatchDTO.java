package com.codecool.codekickfc.controller.dto.matches;

import java.time.LocalDateTime;
import java.util.List;

public record NewMatchDTO(List<Short> subscribed_players_id, double match_fee_per_players, int field_id, LocalDateTime match_date) {
}
