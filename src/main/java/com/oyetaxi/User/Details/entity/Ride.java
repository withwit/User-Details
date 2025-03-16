package com.oyetaxi.User.Details.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rides")
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "passenger", nullable = true)  // This creates a foreign key column in Ride
    private User passenger;  // Reference to the User entity

    @ManyToOne()
    @JoinColumn(name = "driver", nullable = true)  // This creates a foreign key column in Ride
    private User driver;  // Reference to the User entity

    @Column(name = "location_data")
    private List<String> locationData = new ArrayList<>();
    @Column(name = "price_estimate")
    private String priceEstimate;
    private String distance;


    public Ride() {
    }

    public Ride(List<String> locationData, String priceEstimate, String distance) {
        this.priceEstimate = priceEstimate;
        this.distance = distance;
        this.locationData = locationData != null ? locationData : new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getPassenger() {
        return passenger;
    }

    public void setPassenger(User passenger) {
        this.passenger = passenger;
    }

    public void setDriver(User driver) {
        this.driver = driver;
    }

    public User getDriver() {
        return driver;
    }

    public List<String> getLocationData() {
        return locationData;
    }

    public void setLocationData(List<String> locationData) {
        this.locationData.add(String.valueOf(locationData));
    }

    public String getPriceEstimate() {
        return priceEstimate;
    }

    public void setPriceEstimate(String priceEstimate) {
        this.priceEstimate = priceEstimate;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "Ride{" +
                "id=" + id +
                ", passenger=" + passenger +
                ", driver=" + driver +
                ", locationData='" + locationData + '\'' +
                ", priceEstimate='" + priceEstimate + '\'' +
                ", distance='" + distance + '\'' +
                '}';
    }
}
