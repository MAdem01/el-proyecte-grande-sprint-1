package com.codecool.codekickfc.controller.matches;

import com.codecool.codekickfc.controller.dto.matches.MatchDTO;
import com.codecool.codekickfc.controller.dto.matches.MatchIdDTO;
import com.codecool.codekickfc.controller.dto.matches.NewMatchDTO;
import com.codecool.codekickfc.controller.dto.matches.UpdateMatchDTO;
import com.codecool.codekickfc.service.matches.MatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/matches")
public class MatchController {

    private final MatchService matchService;

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
    public ResponseEntity<List<MatchDTO>> getAllMatches(
            @RequestParam(required = false) String area,
            @RequestParam(defaultValue = "0") int pageNumber
    ) {
        return ResponseEntity.ok(matchService.getAllMatches(area, pageNumber));
    }

    /**
     * Create a new match from the provided information and saves into the database.
     *
     * @param newMatchDTO The request body based on the client inputs.
     * @return ID of the newly created match.
     */
    @PostMapping
    public ResponseEntity<MatchIdDTO> createMatch(@RequestBody NewMatchDTO newMatchDTO) {
        return ResponseEntity.ok(matchService.createMatch(newMatchDTO));
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
    public ResponseEntity<MatchIdDTO> updateMatch(@PathVariable long matchId,
                                            @RequestBody UpdateMatchDTO updateMatchDetails) {
        return ResponseEntity.ok(matchService.updateMatch(updateMatchDetails, matchId));
    }

    /**
     * Delete an existing match based on the provided ID and saves the changes in the
     * database.
     *
     * @param matchId ID of the match client wants to delete.
     * @return ID of the deleted match
     */
    @DeleteMapping("/{matchId}")
    public ResponseEntity<MatchIdDTO> deleteMatch(@PathVariable long matchId) {
        return ResponseEntity.ok(matchService.deleteMatch(matchId));
    }

    /**
     * Get an existing match from the database based on the provided ID.
     *
     * @param matchId ID of the match client wants to get.
     * @return Match data transfer object that includes match's id, fee, max capacity,
     * football field, match date, rules, subscribed players.
     */
    @GetMapping("/{matchId}")
    public ResponseEntity<MatchDTO> getMatchById(@PathVariable long matchId) {
        return ResponseEntity.ok(matchService.getMatchById(matchId));
    }
}
