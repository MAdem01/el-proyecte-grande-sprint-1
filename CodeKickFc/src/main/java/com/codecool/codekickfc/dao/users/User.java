package com.codecool.codekickfc.dao.users;

import java.sql.Array;

public record User(int id, String username, String firstName,
                   String lastName, String password, String email, Array matchId) {
}
