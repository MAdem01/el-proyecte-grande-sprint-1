package com.codecool.codekickfc.controller.dto.pitches;

import com.codecool.codekickfc.dao.model.pitches.FootballPitch;

public record FootballPitchDTO(long id, String name, String description,
                               String pitchType, String city, String district, String postcode,
                               String streetName, String streetNumber) {

    public static FootballPitchDTO fromFootballPitch(FootballPitch footballPitch) {
        return new FootballPitchDTO(
                footballPitch.getId(),
                footballPitch.getPitchName(),
                footballPitch.getPitchDescription(),
                footballPitch.getPitchType(),
                footballPitch.getCity(),
                footballPitch.getDistrict(),
                footballPitch.getPostcode(),
                footballPitch.getStreetName(),
                footballPitch.getStreetNumber()
        );
    }
}
