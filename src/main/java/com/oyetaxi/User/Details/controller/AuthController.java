package com.oyetaxi.User.Details.controller;

import com.oyetaxi.User.Details.dto.AuthResponse;
import com.oyetaxi.User.Details.dto.LoginRequest;
import com.oyetaxi.User.Details.dto.UserDataDTO;
import com.oyetaxi.User.Details.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        String token = authenticationService.loginUser(request);
        return ResponseEntity.status(HttpStatus.OK).body(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> createUser(@RequestBody UserDataDTO dataDTO) {
        String token = authenticationService.registerUser(dataDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new AuthResponse(token));
    }
}

