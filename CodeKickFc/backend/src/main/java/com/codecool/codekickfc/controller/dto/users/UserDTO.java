package com.codecool.codekickfc.controller.dto.users;

import com.codecool.codekickfc.dao.model.matches.Match;

import java.util.List;

public record UserDTO(String username, String firstName, String lastName,
                      String email, List<Match> matches) {
}
