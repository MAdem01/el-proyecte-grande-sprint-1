package com.codecool.codekickfc.repository.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Entity
@Table(name = "player")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", initialValue = 100, allocationSize = 1)
    private long id;
    @Setter
    @Column(unique = true, nullable = false)
    private String username;
    @Setter
    @Column(nullable = false)
    private String firstName;
    @Setter
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String password;
    @Setter
    @Column(unique = true, nullable = false)
    private String email;
    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<Match> matches;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Role> roles;

    public User(String username, String firstName, String lastName, String password, String email) {
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.email = email;
        this.matches = new ArrayList<>();
        this.roles = new HashSet<>();
    }

    public User() {
    }

    public void addMatch(Match match) {
        this.matches.add(match);
        match.addUser(this);
    }

    public void addRole(Role role) {
        this.roles.add(role);
    }

    public void removeMatch(Match match) {
        this.matches.remove(match);
    }
}
