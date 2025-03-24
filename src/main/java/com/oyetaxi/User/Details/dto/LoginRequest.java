package com.oyetaxi.User.Details.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class LoginRequest {

    @NotBlank(message = "Id is required")
    private Long id;

    @NotBlank(message = "Password is required")
    private String password;
}
