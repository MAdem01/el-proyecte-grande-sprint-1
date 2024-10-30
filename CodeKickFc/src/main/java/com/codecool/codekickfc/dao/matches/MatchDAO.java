package com.codecool.codekickfc.dao.matches;

import com.codecool.codekickfc.controller.dto.matches.NewMatchDTO;
import com.codecool.codekickfc.dao.model.matches.Match;

import java.util.List;

public interface MatchDAO {
    List<Match> getAllMatches();
    Match getMatchById(int matchId);
    int postMatch(NewMatchDTO matchDTO);
}
