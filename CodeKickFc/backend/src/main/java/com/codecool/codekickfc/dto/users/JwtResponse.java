package com.codecool.codekickfc.dto.users;

import java.util.List;

public record JwtResponse(String jwt, String username, long id, List<String> roles) {
}
