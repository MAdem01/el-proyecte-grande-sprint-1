package com.codecool.codekickfc.dao.footballpitch;

import com.codecool.codekickfc.controller.dto.pitches.NewFootballPitchDTO;
import com.codecool.codekickfc.controller.dto.pitches.UpdateFootballPitchDTO;
import com.codecool.codekickfc.dao.model.pitches.FootballPitch;

import java.util.List;

public interface FootballPitchDAO {
    List<FootballPitch> getAllFootballPitch();
    FootballPitch getFootballPitchById(Long fieldId);
    long postNewFootballPitch(NewFootballPitchDTO footballPitchDTO);
    long updateFootballPitch(Long fieldId, NewFootballPitchDTO footballPitchDTO);
    boolean deleteFootballPitch(long fieldId);
}
