package com.spnsolo.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "scooter")
public class Scooter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 3,name = "charge")
    private int charge;

    @Column(nullable = false,name = "available")
    private boolean available;

    @Embedded
    private Coordinates coordinates;

    public Scooter() {
    }

    public Scooter(Long id, int charge, boolean available, Coordinates coordinates) {
        this.id = id;
        this.charge = charge;
        this.available = available;
        this.coordinates = coordinates;
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

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Scooter scooter = (Scooter) o;
        return charge == scooter.charge && available == scooter.available && id.equals(scooter.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, charge, available);
    }
}
