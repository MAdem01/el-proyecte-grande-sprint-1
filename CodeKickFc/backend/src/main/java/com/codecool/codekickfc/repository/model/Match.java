package com.codecool.codekickfc.repository.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "match_sequence")
    @SequenceGenerator(name = "match_sequence", sequenceName = "match_sequence", initialValue = 100, allocationSize = 1)
    private long id;
    @Column(nullable = false)
    private int maxPlayers;
    @Column(nullable = false)
    private double matchFeePerPerson;
    @Column(nullable = false)
    private LocalDateTime matchDate;
    @Column(nullable = false)
    private String matchRules;
    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private FootballPitch footballField;
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private List<User> users;

    public Match(int maxPlayers, double matchFeePerPerson, LocalDateTime matchDate,
                 String matchRules, FootballPitch footballField) {
        this.maxPlayers = maxPlayers;
        this.matchFeePerPerson = matchFeePerPerson;
        this.matchDate = matchDate;
        this.matchRules = matchRules;
        this.footballField = footballField;
        this.users = new ArrayList<>();
    }

    public Match() {
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public double getMatchFeePerPerson() {
        return matchFeePerPerson;
    }

    public void setMatchFeePerPerson(double matchFeePerPerson) {
        this.matchFeePerPerson = matchFeePerPerson;
    }

    public LocalDateTime getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(LocalDateTime matchDate) {
        this.matchDate = matchDate;
    }

    public List<String> getMatchRules() {
        return new ArrayList<>(Arrays.asList(matchRules.split(",")));
    }

    public void setMatchRules(String matchRules) {
        this.matchRules = matchRules;
    }

    public FootballPitch getFootballField() {
        return footballField;
    }

    public void setFootballField(FootballPitch footballField) {
        this.footballField = footballField;
    }

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void removeUser(User user) {
        this.users.remove(user);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
