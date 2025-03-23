package com.oyetaxi.User.Details.entity;


import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    @NotBlank(message = "Id is required")
    private Long id;

    @NotBlank(message = "Password is required")
    private String password;
}
