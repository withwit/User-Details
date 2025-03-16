package com.oyetaxi.User.Details.entity;

import com.oyetaxi.User.Details.dto.UserDataDTO;
import jakarta.persistence.Entity;

@Entity
public class Driver extends User {
    private String licenseNumber;
    private String rating;

    public Driver(UserDataDTO dataDTO) {
        super(dataDTO.getUser());
        this.licenseNumber=dataDTO.getLicenceNumber();
        this.rating=dataDTO.getRating();
    }

    public Driver() {
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }
}
