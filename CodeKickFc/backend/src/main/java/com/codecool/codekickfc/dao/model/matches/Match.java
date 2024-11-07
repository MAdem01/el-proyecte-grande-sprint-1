package com.codecool.codekickfc.dao.model.matches;

import com.codecool.codekickfc.dao.model.pitches.FootballPitch;
import com.codecool.codekickfc.dao.model.users.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Match {
    @Id
    @GeneratedValue
    private long id;
    @Column(nullable = false)
    private int maxPlayers;
    @Column(nullable = false)
    private double matchFeePerPerson;
    @Column(nullable = false)
    private LocalDateTime matchDate;
    @Column(nullable = false)
    private String matchRules;
    @OneToOne
    private FootballPitch footballField;
    @ManyToMany
    private List<User> users;

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

    public String getMatchRules() {
        return matchRules;
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

    public long getId() {
        return id;
    }
}
