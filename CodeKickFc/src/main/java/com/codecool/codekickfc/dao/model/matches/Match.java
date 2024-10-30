package com.codecool.codekickfc.dao.model.matches;

import java.sql.Array;
import java.time.LocalDateTime;

public record Match(int match_id, Array subscribed_players_id, double match_fee_per_players, int field_id, LocalDateTime match_date) {
}
