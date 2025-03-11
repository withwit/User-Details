package com.oyetaxi.User.Details.entity;

public class Driver implements  UserInterface{
    private User user;

    public Driver(User user)  {
        this.user = user;
    }

    public Long getId() {
        return user.getId();
    }

    public String getUserType() {
        return user.getUserType();
    }
}
