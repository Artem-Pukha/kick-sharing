package com.spnsolo.model;

import com.spnsolo.entity.Coordinates;
import com.spnsolo.entity.Scooter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

public class SaveScooter {

    @Min(value = 0,message = "Negative value of charge")
    @Max(value = 100,message = "Too big value of charge")
    private int charge;

    private boolean available = true;

    private double longitude;

    private double latitude;

    public SaveScooter() { }

    public SaveScooter(int charge, boolean available, double longitude, double latitude) {
        this.charge = charge;
        this.available = available;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public Scooter toScooter(){
        Scooter scooter = new Scooter();
        scooter.setCoordinates(new Coordinates(longitude,latitude));
        scooter.setCharge(charge);
        scooter.setAvailable(available);
        return scooter;
    }

    public int getCharge() { return charge; }

    public void setCharge(int charge) { this.charge = charge; }

    public boolean isAvailable() { return available; }

    public void setAvailable(boolean available) { this.available = available; }

    public double getLongitude() { return longitude; }

    public void setLongitude(double longitude) { this.longitude = longitude; }

    public double getLatitude() { return latitude; }

    public void setLatitude(double latitude) { this.latitude = latitude; }
}
