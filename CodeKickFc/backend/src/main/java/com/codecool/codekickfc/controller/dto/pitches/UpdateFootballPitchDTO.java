package com.codecool.codekickfc.controller.dto.pitches;

public record UpdateFootballPitchDTO(String pitchName, String description,
                                     String pitchType, String city, String district, String postcode,
                                     String streetName, String streetNumber, String imgUrl) {
}
