package com.oyetaxi.User.Details.controller;

import com.oyetaxi.User.Details.dto.*;
import com.oyetaxi.User.Details.entity.User;
import com.oyetaxi.User.Details.service.AuthenticationService;
import com.oyetaxi.User.Details.service.JwtService;
import com.oyetaxi.User.Details.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authenticationService;
    private final JwtService jwtService;
    private final UserService userService;

    public AuthController(AuthenticationService authenticationService, JwtService jwtService, UserService userService) {
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody UserDataDTO dataDTO) {
        User user = authenticationService.register(dataDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        User authenticatedUser = authenticationService.login(request);
        String token = jwtService.generateToken(authenticatedUser);
        long expirationTime = jwtService.getExpirationTime();

        return ResponseEntity.status(HttpStatus.OK).body(new AuthResponse(token, expirationTime));
    }

    @PostMapping("/silent-login")
    public ResponseEntity<?> silentLogin(@RequestBody TokenRequest request) {
        String expiredToken = request.getLastToken();

        try {
            String username = jwtService.extractUsername(expiredToken);

            if (jwtService.isTokenExpired(expiredToken)) {
                UserDetails userDetails = userService.getUserByMobileNumber(username);

                if (userDetails != null) {
                    String newToken = jwtService.generateToken(userDetails);
                    return ResponseEntity.ok(new TokenResponse(newToken));
                }
            }
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid token or user does not exist");
        }

        return ResponseEntity.status(401).body("Token is still valid or invalid");
    }
}




