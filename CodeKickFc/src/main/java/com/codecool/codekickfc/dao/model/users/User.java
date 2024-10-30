package com.codecool.codekickfc.dao.model.users;

import java.sql.Array;

public class User {
    private final int id;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private final Array matchIds;

    public User(int id, String username, String firstName,
                String lastName, String password, String email, Array matchIds) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.matchIds = matchIds;
    }

    public User(int id, String username, String firstName, String lastName, String password, String email) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.matchIds = null;
    }
}
