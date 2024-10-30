package com.codecool.codekickfc.controller.footballpitch;

import com.codecool.codekickfc.controller.dto.pitches.FootballPitchDTO;
import com.codecool.codekickfc.controller.dto.pitches.FootballPitchDeleteDTO;
import com.codecool.codekickfc.controller.dto.pitches.FootballPitchIdDTO;
import com.codecool.codekickfc.controller.dto.pitches.NewFootballPitchDTO;
import com.codecool.codekickfc.service.pitches.FootballPitchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class FootballPitchController {

    private final FootballPitchService footballPitchService;

    @Autowired
    public FootballPitchController(FootballPitchService footballPitchService) {
        this.footballPitchService = footballPitchService;
    }

    /**
     * Handles HTTP GET requests to retrieve all football pitch data.
     * This method calls the service layer to obtain a list of {@link FootballPitchDTO} objects,
     * which represent the available football pitches.
     *
     * @return a list of {@link FootballPitchDTO} records containing details of all football pitches.
     *         Returns an empty list if no pitches are found.
     */
    @GetMapping("/api/fields")
    public List<FootballPitchDTO> getFootballPitches() {
        return footballPitchService.getAllFootballPitches();
    }

    @GetMapping("/api/fields/{fieldId}")
    public FootballPitchDTO getFootballPitchById(@PathVariable Long fieldId) {
        return footballPitchService.getFootballPitchById(fieldId);
    }

    @PostMapping("/api/fields")
    public FootballPitchIdDTO postFootballPitch(@RequestBody NewFootballPitchDTO footballPitchDTO) {
        return footballPitchService.addNewFootballPitch(footballPitchDTO);
    }

    @DeleteMapping("/api/fields/{fieldId}")
    public FootballPitchDeleteDTO deleteFootballPitch(@PathVariable long fieldId) {
        return footballPitchService.deleteFootballPitch(fieldId);
    }
}
