package com.codecool.codekickfc.controller.users;

public record UpdateUserDTO(String username, String firstName,
                            String lastName, String email, String password) {
}
