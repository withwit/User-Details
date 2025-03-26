package com.oyetaxi.User.Details.dto;

public class TokenResponse {
    private String newToken;

    public TokenResponse(String newToken) {
        this.newToken = newToken;
    }

    public String getNewToken() {
        return newToken;
    }
}