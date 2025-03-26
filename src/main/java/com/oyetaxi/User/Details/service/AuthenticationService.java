package com.oyetaxi.User.Details.service;

import com.oyetaxi.User.Details.dto.LoginRequest;
import com.oyetaxi.User.Details.dto.UserDataDTO;
import com.oyetaxi.User.Details.entity.User;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserService userService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(UserDataDTO dataDTO) {
        User user = dataDTO.getUser();
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userService.createUser(dataDTO);
    }

    public User login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getMobileNumber(), request.getPassword()));

        return userService.getUserByMobileNumber(request.getMobileNumber());
    }
}