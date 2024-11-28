package com.codecool.codekickfc.controller;

import com.codecool.codekickfc.dto.matches.MatchDTO;
import com.codecool.codekickfc.dto.matches.MatchIdDTO;
import com.codecool.codekickfc.dto.matches.NewMatchDTO;
import com.codecool.codekickfc.dto.matches.UpdateMatchDTO;
import com.codecool.codekickfc.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/matches")
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    /**
     * Extracts available information from all existing matches in the database.
     *
     * @return list of transformed match object that includes match's id, fee, max capacity,
     * football field, match date, rules, subscribed players.
     */
    @GetMapping
    public List<MatchDTO> getAllMatches(
            @RequestParam(required = false) String area,
            @RequestParam(defaultValue = "0") int pageNumber
    ) {
        return matchService.getAllMatches(area, pageNumber);
    }

    /**
     * Create a new match from the provided information and saves into the database.
     *
     * @param newMatchDTO The request body based on the client inputs.
     * @return ID of the newly created match.
     */
    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public MatchIdDTO createMatch(@RequestBody NewMatchDTO newMatchDTO) {
        return matchService.createMatch(newMatchDTO);
    }

    /**
     * Update an existing match based on the provided information and saves the changes in the
     * database.
     *
     * @param updateMatchDetails The request body based on the client inputs.
     * @param matchId            ID of the match client wants to update.
     * @return ID of the updated match.
     */
    @PutMapping("/{matchId}")
    public MatchIdDTO updateMatch(@PathVariable long matchId,
                                            @RequestBody UpdateMatchDTO updateMatchDetails) {
        return matchService.updateMatch(updateMatchDetails, matchId);
    }

    /**
     * Delete an existing match based on the provided ID and saves the changes in the
     * database.
     *
     * @param matchId ID of the match client wants to delete.
     * @return ID of the deleted match
     */
    @DeleteMapping("/{matchId}")
    public MatchIdDTO deleteMatch(@PathVariable long matchId) {
        return matchService.deleteMatch(matchId);
    }

    /**
     * Get an existing match from the database based on the provided ID.
     *
     * @param matchId ID of the match client wants to get.
     * @return Match data transfer object that includes match's id, fee, max capacity,
     * football field, match date, rules, subscribed players.
     */
    @GetMapping("/{matchId}")
    public MatchDTO getMatchById(@PathVariable long matchId) {
        return matchService.getMatchById(matchId);
    }
}
