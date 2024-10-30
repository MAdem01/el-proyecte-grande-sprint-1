package com.codecool.codekickfc.dao.model.users;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class User {
    private final int id;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private final List<Short> matchIds;

    public User(int id, String username, String firstName,
                String lastName, String password, String email, List<Short> matchIds) {
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
        this.matchIds = new ArrayList<>();
    }

    public int id() {
        return id;
    }

    public String username() {
        return username;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public String email() {
        return email;
    }

    public List<Short> matchIds() {
        return matchIds;
    }
}
