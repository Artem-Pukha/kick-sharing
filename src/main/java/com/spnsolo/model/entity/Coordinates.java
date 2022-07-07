package com.spnsolo.model.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Coordinates {
    @Column(nullable = false)
    private double longitude;

    @Column(nullable = false)
    private double latitude;

    public Coordinates() {
    }

    public Coordinates(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
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
