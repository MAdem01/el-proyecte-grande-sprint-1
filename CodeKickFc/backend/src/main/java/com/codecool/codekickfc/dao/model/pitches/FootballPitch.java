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

    public FootballPitch(String pitchName, String pitchDescription,
                         String pitchType, String address) {
        this.pitchName = pitchName;
        this.pitchDescription = pitchDescription;
        this.pitchType = pitchType;
        this.address = address;
    }

    public FootballPitch() {
    }

    public long getId() {
        return id;
    }

    public String getPitchName() {
        return pitchName;
    }

    public void setPitchName(String pitchName) {
        this.pitchName = pitchName;
    }

    public String getPitchDescription() {
        return pitchDescription;
    }

    public void setPitchDescription(String pitchDescription) {
        this.pitchDescription = pitchDescription;
    }

    public String getPitchType() {
        return pitchType;
    }

    public void setPitchType(String pitchType) {
        this.pitchType = pitchType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
