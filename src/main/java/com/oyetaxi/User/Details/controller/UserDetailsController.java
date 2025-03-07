package com.oyetaxi.User.Details.controller;

import com.oyetaxi.User.Details.entity.Ride;
import com.oyetaxi.User.Details.entity.User;
import com.oyetaxi.User.Details.entity.UserInterface;
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
    public ResponseEntity<UserInterface> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.
                createUser(
                        user.getName(),
                        user.getUserType(),
                        user.getMobileNumber(),
                        user.getEmail(),
                        user.getCurrentLoc()
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserInterface> getUserById(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User user) {
        return ResponseEntity.ok(userService.updateUser(id, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/rides/{id}")
    public ResponseEntity<List<Ride>> getUserRides(@PathVariable String id) {
        return ResponseEntity.ok(userService.getUserRides(id));
    }

    @PostMapping("/rides/{id}")
    public ResponseEntity<Void> addUserRide(@PathVariable String id, @RequestBody Ride ride) {
        return ResponseEntity.ok(userService.addUserRide(id, ride));
    }


}
