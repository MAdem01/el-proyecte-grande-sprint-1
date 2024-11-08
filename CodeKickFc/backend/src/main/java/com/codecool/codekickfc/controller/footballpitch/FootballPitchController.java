package com.codecool.codekickfc.controller.footballpitch;

import com.codecool.codekickfc.controller.dto.pitches.FootballPitchDTO;
import com.codecool.codekickfc.controller.dto.pitches.FootballPitchIdDTO;
import com.codecool.codekickfc.controller.dto.pitches.NewFootballPitchDTO;
import com.codecool.codekickfc.controller.dto.pitches.UpdateFootballPitchDTO;
import com.codecool.codekickfc.service.pitches.FootballPitchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/footballpitches")
public class FootballPitchController {

    private final FootballPitchService footballPitchService;

    public FootballPitchController(FootballPitchService footballPitchService) {
        this.footballPitchService = footballPitchService;
    }

    /**
     * Extracts available information from all existing footballPitch in the database.
     *
     * @return list of transformed footballPitch object that includes footballPitch's id, name,
     * type, address.
     */
    @GetMapping
    public ResponseEntity<List<FootballPitchDTO>> getAllFootballPitches() {
        return ResponseEntity.ok(footballPitchService.getAllFootballPitches());
    }

    /**
     * Create a new footballPitch from the provided information and saves into the database.
     *
     * @param newFootballPitchDTO The request body based on the client inputs.
     * @return ID of the newly created footballPitch.
     */
    @PostMapping
    public ResponseEntity<FootballPitchIdDTO> createFootballPitch(@RequestBody NewFootballPitchDTO newFootballPitchDTO) {
        return ResponseEntity.ok(footballPitchService.createFootballPitch(newFootballPitchDTO));
    }

    /**
     * Update an existing footballPitch based on the provided information and saves the changes in the
     * database.
     *
     * @param updateFootballPitchDetails The request body based on the client inputs.
     * @param footballPitchId            ID of the footballPitch client wants to update.
     * @return ID of the updated footballPitch.
     */
    @PutMapping("/{footballPitchId}")
    public ResponseEntity<FootballPitchIdDTO> updateFootballPitch(
            @PathVariable long footballPitchId,
            @RequestBody UpdateFootballPitchDTO updateFootballPitchDetails
    ) {
        return ResponseEntity.ok(footballPitchService.updateFootballPitch(updateFootballPitchDetails, footballPitchId));
    }

    /**
     * Get an existing footballPitch from the database based on the provided ID.
     *
     * @param footballPitchId ID of the footballPitch client wants to get.
     * @return FootballPitch data transfer object that includes footballPitch's id, name, type,
     * address.
     */
    @GetMapping("/{footballPitchId}")
    public ResponseEntity<FootballPitchDTO> getFootballPitchById(
            @PathVariable long footballPitchId
    ) {
        return ResponseEntity.ok(footballPitchService.getFootballPitchById(footballPitchId));
    }
}
