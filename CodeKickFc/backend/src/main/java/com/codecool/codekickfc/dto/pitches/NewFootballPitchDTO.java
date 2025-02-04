package com.codecool.codekickfc.dto.pitches;

public record NewFootballPitchDTO(String pitchName, String description,
                                  String pitchType, String city, String district, String postcode,
                                  String streetName, String streetNumber, String imgUrl,
                                  double longitude, double latitude) {
}
