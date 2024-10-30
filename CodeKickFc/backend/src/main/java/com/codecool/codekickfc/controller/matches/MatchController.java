package com.codecool.codekickfc.controller.matches;

import com.codecool.codekickfc.controller.dto.matches.MatchDTO;
import com.codecool.codekickfc.controller.dto.matches.MatchIdDTO;
import com.codecool.codekickfc.controller.dto.matches.NewMatchDTO;
import com.codecool.codekickfc.service.matches.MatchService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MatchController {

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/api/matches")
    public List<MatchDTO> getAllMatches() {
        return matchService.getAllMatches();
    }

    @GetMapping("/api/matches/{matchId}")
    public MatchDTO getMatchById(@PathVariable int matchId) {
        return matchService.getMatchById(matchId);
    }

    @PostMapping("/api/matches")
    public MatchIdDTO postMatch(@RequestBody NewMatchDTO matchDTO) {
        return matchService.postMatch(matchDTO);
    }
}
