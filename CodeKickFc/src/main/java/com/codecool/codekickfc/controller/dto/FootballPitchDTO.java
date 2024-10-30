package com.codecool.codekickfc.controller.dto;

import com.codecool.codekickfc.dao.model.FootballPitch;

public record FootballPitchDTO(int Id, String name, int maxPlayers, double price, String address) {

    public static FootballPitchDTO fromFootballPitch(FootballPitch footballPitch) {
        return new FootballPitchDTO(
                footballPitch.Id(),
                footballPitch.name(),
                footballPitch.maxPlayers(),
                footballPitch.price(),
                footballPitch.address());
    }
}
