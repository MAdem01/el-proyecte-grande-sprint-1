package com.codecool.codekickfc.dto.pitches;

import com.codecool.codekickfc.repository.model.FootballPitch;

public record FootballPitchDTO(long id, String name, String description,
                               String pitchType, String city, String district, String postcode,
                               String streetName, String streetNumber, String imgUrl,
                               double longitude, double latitude) {

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
                footballPitch.getStreetNumber(),
                footballPitch.getImgUrl(),
                footballPitch.getLongitude(),
                footballPitch.getLatitude()
        );
    }
}
