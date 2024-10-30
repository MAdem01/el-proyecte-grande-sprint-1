package com.codecool.codekickfc.controller.footballpitch;

import com.codecool.codekickfc.controller.dto.FootballPitchDTO;
import com.codecool.codekickfc.controller.dto.FootballPitchIdDTO;
import com.codecool.codekickfc.controller.dto.NewFootballPitchDTO;
import com.codecool.codekickfc.service.FootballPitchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/api/fields")
    public FootballPitchIdDTO postFootballPitch(@RequestBody NewFootballPitchDTO footballPitchDTO) {
        return footballPitchService.addNewFootballPitch(footballPitchDTO);
    }
}
