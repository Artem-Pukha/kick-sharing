package com.spnsolo.model;

import com.spnsolo.entity.Coordinates;
import com.spnsolo.entity.Scooter;

import java.util.ArrayList;
import java.util.List;

public class ScooterResponse {

    private long id;

    private int charge;

    private boolean available = true;

    private double longitude;

    private double latitude;

    public static ScooterResponse fromScooter(Scooter scooter){
        ScooterResponse scooterResponse = new ScooterResponse();

        scooterResponse.setAvailable(scooter.isAvailable());
        scooterResponse.setCharge(scooter.getCharge());
        scooterResponse.setId(scooter.getId());

        Coordinates coordinates = scooter.getCoordinates();

        scooterResponse.setLatitude(coordinates.getLatitude());
        scooterResponse.setLongitude(coordinates.getLongitude());

        return scooterResponse;
    }

    public static List<ScooterResponse> fromScooter(List<Scooter> scooters){
        List<ScooterResponse> scooterResponses = new ArrayList<>();
        for(Scooter scooter : scooters){
            scooterResponses.add(fromScooter(scooter));
        }
        return scooterResponses;
    }

    public ScooterResponse(){}

    public ScooterResponse(Long id, int charge, boolean available, double longitude, double latitude) {
        this.id = id;
        this.charge = charge;
        this.available = available;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCharge() {
        return charge;
    }

    public void setCharge(int charge) {
        this.charge = charge;
    }

    public boolean isAvailable() { return available; }

    public void setAvailable(boolean available) {
        this.available = available;
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
