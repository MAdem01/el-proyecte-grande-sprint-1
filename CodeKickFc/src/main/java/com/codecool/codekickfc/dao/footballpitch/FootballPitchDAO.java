package com.codecool.codekickfc.dao.footballpitch;

import com.codecool.codekickfc.controller.dto.NewFootballPitchDTO;
import com.codecool.codekickfc.dao.model.FootballPitch;

import java.util.List;

public interface FootballPitchDAO {
    List<FootballPitch> getAllFootballPitch();
    long postNewFootballPitch(NewFootballPitchDTO footballPitchDTO);
}
