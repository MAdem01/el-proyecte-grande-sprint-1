package com.codecool.codekickfc.dao.model.pitches;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class FootballPitch {
    @Id
    @GeneratedValue
    private long id;
    @Column(unique = true, nullable = false)
    private String pitchName;
    @Column(nullable = false)
    private String pitchDescription;
    @Column(nullable = false)
    private String pitchType;
    @Column(unique = true, nullable = false)
    private String address;
}
