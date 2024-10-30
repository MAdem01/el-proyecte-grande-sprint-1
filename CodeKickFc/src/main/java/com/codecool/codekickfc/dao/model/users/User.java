package com.codecool.codekickfc.dao.users;

public record User(int id, String username, String firstName,
                   String lastName, String password, String email) {
}
