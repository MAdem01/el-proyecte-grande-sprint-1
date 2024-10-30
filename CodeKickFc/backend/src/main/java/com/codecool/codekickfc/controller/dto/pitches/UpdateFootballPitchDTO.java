package com.codecool.codekickfc.controller.dto.pitches;

public record UpdateFootballPitchDTO(String name, int maxPlayers, double price, int postCode, String city, String street, int street_number) {
}
