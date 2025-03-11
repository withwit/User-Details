package com.oyetaxi.User.Details.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private LocalDateTime createDt;
    private LocalDateTime updateDt;
    private String name;
    private String mobileNumber;
    private String email;
    private String currentLoc;

    @OneToMany(mappedBy = "passenger", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ride> rides;

    public User(String name, String type, String mobileNumber, String email, String currentLoc) {
        LocalDateTime nowDate = LocalDateTime.now();
        this.createDt = nowDate;
        this.updateDt = nowDate;
        this.name = name;
        this.type = type;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.currentLoc = currentLoc;
        this.rides = new ArrayList<>();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateDt = LocalDateTime.now();
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getCreateDt() {
        return createDt;
    }

    public void setCreateDt(LocalDateTime createDt) {
        this.createDt = createDt;
    }

    public LocalDateTime getUpdateDt() {
        return updateDt;
    }

    public void setUpdateDt(LocalDateTime updateDt) {
        this.updateDt = updateDt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCurrentLoc() {
        return currentLoc;
    }

    public void setCurrentLoc(String currentLoc) {
        this.currentLoc = currentLoc;
    }

    public List<Ride> getRides() {
        return rides;
    }

    public void setRides(Ride ride) {
        this.rides.add(ride);
    }

    public String getUserType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", createDt=" + createDt +
                ", updateDt=" + updateDt +
                ", name='" + name + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", email='" + email + '\'' +
                ", currentLoc='" + currentLoc + '\'' +
                ", rides=" + rides +
                '}';
    }


}


