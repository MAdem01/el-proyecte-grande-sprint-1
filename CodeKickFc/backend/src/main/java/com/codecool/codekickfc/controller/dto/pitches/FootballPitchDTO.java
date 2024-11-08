package com.codecool.codekickfc.controller.dto.pitches;

import com.codecool.codekickfc.dao.model.pitches.FootballPitch;

public record FootballPitchDTO(long Id, String name, String description,
                               String pitchType, String address) {

    public static FootballPitchDTO fromFootballPitch(FootballPitch footballPitch) {
        return new FootballPitchDTO(
                footballPitch.getId(),
                footballPitch.getPitchName(),
                footballPitch.getPitchDescription(),
                footballPitch.getPitchType(),
                footballPitch.getAddress());
    }
}
