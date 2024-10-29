package com.codecool.codekickfc.controller;

import com.codecool.codekickfc.controller.dto.FootballPitchDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FootballPitchController {

    @GetMapping("/api/fields")
    public List<FootballPitchDTO> getFootballPitches() {
        return null;
    }
}
