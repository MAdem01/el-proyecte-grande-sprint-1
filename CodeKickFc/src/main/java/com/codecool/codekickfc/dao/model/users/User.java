package com.codecool.codekickfc.dao.model.users;

public record User(int id, String username, String firstName,
                   String lastName, String password, String email) {
}
