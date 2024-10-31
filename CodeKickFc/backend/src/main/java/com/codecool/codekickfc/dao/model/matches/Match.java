package com.codecool.codekickfc.dao.model.matches;

import java.time.LocalDateTime;
import java.util.List;

public record Match(int match_id, List<Short> subscribed_players_id, double match_fee_per_players, int field_id, LocalDateTime match_date) { }
