package com.oyetaxi.User.Details.controller;

import com.oyetaxi.User.Details.dto.UserDataDTO;
import com.oyetaxi.User.Details.entity.Ride;
import com.oyetaxi.User.Details.entity.User;
import com.oyetaxi.User.Details.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserDetailsController {
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserDataDTO dataDTO) {
        return ResponseEntity.ok(userService.createUser(dataDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody UserDataDTO dataDTO) {
        return ResponseEntity.ok(userService.updateUser(id, dataDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/rides")
    public ResponseEntity<List<Ride>> getUserRides(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserRides(id));
    }

    @PutMapping("/{id}/rides")
    public void addUserRide(@PathVariable Long id, @RequestBody Ride ride) {
        userService.addUserRide(id, ride);
    }


}
