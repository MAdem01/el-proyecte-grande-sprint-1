package com.codecool.codekickfc.controller.dto.users;

import java.sql.Array;

public record UserDTO(String username, String firstName,
                      String lastName, String email, Array matchIds) {
}
