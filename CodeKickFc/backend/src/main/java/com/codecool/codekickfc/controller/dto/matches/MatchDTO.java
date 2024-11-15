package com.codecool.codekickfc.controller.dto.matches;

import com.codecool.codekickfc.controller.dto.pitches.FootballPitchDTO;
import com.codecool.codekickfc.controller.dto.users.SimpleUserDTO;
import com.codecool.codekickfc.dao.model.matches.Match;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public record MatchDTO(long match_id, double match_fee_per_players, int maxPlayers,
                       FootballPitchDTO footballPitch, LocalDateTime match_date,
                       List<String> matchRules, List<SimpleUserDTO> subscribedPlayers) {

    public static MatchDTO fromMatch(Match match) {
        return new MatchDTO(
                match.getId(),
                match.getMatchFeePerPerson(),
                match.getMaxPlayers(),
                FootballPitchDTO.fromFootballPitch(match.getFootballField()),
                match.getMatchDate(),
                match.getMatchRules(),
                match.getUsers().stream().
                        map(SimpleUserDTO::fromUser).
                        collect(Collectors.toList())
        );
    }
}
