package com.codecool.codekickfc.repository.model;

import jakarta.persistence.*;

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

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(String streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
