package com.codecool.codekickfc.service.matches;

import com.codecool.codekickfc.controller.dto.matches.MatchDTO;
import com.codecool.codekickfc.controller.dto.matches.MatchIdDTO;
import com.codecool.codekickfc.controller.dto.matches.NewMatchDTO;
import com.codecool.codekickfc.dao.matches.MatchDAO;
import com.codecool.codekickfc.dao.model.matches.Match;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchService {

    private final MatchDAO matchDAO;

    @Autowired
    public MatchService(MatchDAO matchDAO) {
        this.matchDAO = matchDAO;
    }

    public List<MatchDTO> getAllMatches() {
        List<Match> matchList = matchDAO.getAllMatches();
        List<MatchDTO> matchDTOList = new ArrayList<>();

        for (Match match : matchList) {
            matchDTOList.add(MatchDTO.fromMatch(match));
        }
        return matchDTOList;
    }

    public MatchDTO getMatchById(int matchId) {
        return MatchDTO.fromMatch(matchDAO.getMatchById(matchId));
    }

    public MatchIdDTO postMatch(NewMatchDTO matchDTO) {
        return MatchIdDTO(matchDAO.postMatch(matchDTO));
    }
}
