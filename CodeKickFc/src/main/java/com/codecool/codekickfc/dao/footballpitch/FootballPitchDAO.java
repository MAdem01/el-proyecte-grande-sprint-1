package com.codecool.codekickfc.dao.footballpitch;

import com.codecool.codekickfc.controller.dto.pitches.NewFootballPitchDTO;
import com.codecool.codekickfc.dao.model.pitches.FootballPitch;

import java.util.List;

public interface FootballPitchDAO {
    List<FootballPitch> getAllFootballPitch();
    long postNewFootballPitch(NewFootballPitchDTO footballPitchDTO);
    boolean deleteFootballPitch(long fieldId);
}
