package com.oyetaxi.User.Details.dto;

import com.oyetaxi.User.Details.entity.User;

public class UserDataDTO {
    private User user;

    //Driver specific
    private String licenceNumber;
    private String rating;

    //Passenger specific


    public User getUser() {
        return user;
    }

    public String getLicenceNumber() {
        return licenceNumber;
    }

    public String getRating() {
        return rating;
    }

    @Override
    public String toString() {
        return "UserDataDTO{" +
                "user=" + user.toString() +
                ", licenceNumber='" + licenceNumber + '\'' +
                ", rating='" + rating + '\'' +
                '}';
    }
}
