package com.codecool.codekickfc.controller.dto.pitches;

public record NewFootballPitchDTO(String name, int maxPlayers, double price, int postCode, String city, String street, int street_number) {
}
