package com.codecool.codekickfc.controller.dto;

import com.codecool.codekickfc.dao.model.FootballPitch;

public record FootballPitchDTO(int Id, String name, int maxPlayers, double price, int postCode, String city, String street, int street_number) {

    public static FootballPitchDTO fromFootballPitch(FootballPitch footballPitch) {
        return new FootballPitchDTO(
                footballPitch.Id(),
                footballPitch.name(),
                footballPitch.maxPlayers(),
                footballPitch.price(),
                footballPitch.postCode(),
                footballPitch.city(),
                footballPitch.street(),
                footballPitch.street_number());
    }
}
