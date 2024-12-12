package com.codecool.codekickfc.repository.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class FootballPitch {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pitch_sequence")
    @SequenceGenerator(name = "pitch_sequence", sequenceName = "pitch_sequence", initialValue = 100, allocationSize = 1)
    private long id;
    @Column(unique = true, nullable = false)
    private String pitchName;
    @Column(nullable = false)
    private String pitchDescription;
    @Column(nullable = false)
    private String pitchType;
    @Column(nullable = false)
    private String city;
    @Column(nullable = false)
    private String district;
    @Column(unique = true, nullable = false)
    private String postcode;
    @Column(nullable = false)
    private String streetName;
    @Column(nullable = false)
    private String streetNumber;
    @Column(nullable = false)
    private String imgUrl;
    @Column(nullable = false)
    private double longitude;
    @Column(nullable = false)
    private double latitude;



    public FootballPitch(String pitchName, String pitchDescription,
                         String pitchType, String city, String district, String postcode,
                         String streetName, String streetNumber, String imgUrl,
                         double longitude, double latitude) {
        this.pitchName = pitchName;
        this.pitchDescription = pitchDescription;
        this.pitchType = pitchType;
        this.city = city;
        this.district = district;
        this.postcode = postcode;
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.imgUrl = imgUrl;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public FootballPitch() {
    }

}
