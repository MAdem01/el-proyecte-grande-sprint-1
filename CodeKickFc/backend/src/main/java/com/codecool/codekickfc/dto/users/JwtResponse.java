package com.codecool.codekickfc.dto.users;

import com.codecool.codekickfc.dto.matches.MatchDTO;

import java.util.List;

public record JwtResponse(String jwt, String username, List<String> roles) {
}
