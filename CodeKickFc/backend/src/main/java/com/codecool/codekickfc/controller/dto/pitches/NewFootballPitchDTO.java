package com.codecool.codekickfc.controller.dto.pitches;

public record NewFootballPitchDTO(String pitchName, String description,
                                  String pitchType, String city, String district, String postcode,
                                  String streetName, String streetNumber) {
}
