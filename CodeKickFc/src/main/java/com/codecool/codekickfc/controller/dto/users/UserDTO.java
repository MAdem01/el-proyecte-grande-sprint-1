package com.codecool.codekickfc.controller.dto.users;

import java.util.List;

public record UserDTO(int userId, String username, String firstName,
                      String lastName, String email, List<Short> matchIds) {
}
