package com.oyetaxi.User.Details.service;

import com.oyetaxi.User.Details.dto.LoginRequest;
import com.oyetaxi.User.Details.dto.UserDataDTO;
import com.oyetaxi.User.Details.entity.User;
import com.oyetaxi.User.Details.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepo userRepository;
    private final JwtService jwtService;

    private final UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;


    public String registerUser(UserDataDTO dataDTO) {
        User user = dataDTO.getUser();
//        user.setPassword(securityConfig.passwordEncoder().encode(user.getPassword()));
        userService.createUser(dataDTO);

        return jwtService.generateToken(user.getId());
    }

    public String loginUser(LoginRequest request) {
        User user = userService.getUserById(request.getId());

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getId(), user.getPassword()));
            User loadedUser = userService.getUserById(user.getId());
            String jwt = jwtService.generateToken(user.getId());
            return jwt;
        } catch (Exception e) {
            throw new RuntimeException("Invalid credentials" + e);
        }

    }
}
