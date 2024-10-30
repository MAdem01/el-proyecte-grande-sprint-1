package com.codecool.codekickfc.controller.dto.matches;

import com.codecool.codekickfc.dao.model.matches.Match;

import java.sql.Array;
import java.time.LocalDateTime;

public record MatchDTO(int match_id, Array subscribed_players_id, double match_fee_per_players, int field_id, LocalDateTime match_date) {

    public static MatchDTO fromMatch(Match match) {
        return new MatchDTO(
                match.match_id(),
                match.subscribed_players_id(),
                match.match_fee_per_players(),
                match.field_id(),
                match.match_date()
        );
    }
}
