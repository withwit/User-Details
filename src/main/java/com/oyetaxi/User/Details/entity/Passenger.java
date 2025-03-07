package com.oyetaxi.User.Details.entity;

public class Passenger implements UserInterface {
    private User user;

    public Passenger(User user) {
        this.user = user;
    }

    public String getId() {
        return user.getId();
    }

    public String getUserType() {
        return user.getUserType();
    }
}
