package com.oyetaxi.User.Details.controller;

import com.oyetaxi.User.Details.entity.Ride;
import com.oyetaxi.User.Details.service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ride")
public class RideController {

    @Autowired
    private RideService rideService;

    @GetMapping("/{id}")
    public ResponseEntity<Ride> getRide(@PathVariable Long id) {
        Ride ride = rideService.getRide(id);
        return ResponseEntity.ok(ride);
    }

    @GetMapping("/{id}/rides")
    public ResponseEntity<List<Ride>> getUserRides(@PathVariable Long id) {
        List<Ride> rides = rideService.getUserRides(id);
        return ResponseEntity.ok(rides);
    }

    @PostMapping("/add/{id}")
    public ResponseEntity<Map<String, String>> addUserRide(@PathVariable Long id, @RequestBody Ride ride) {
        rideService.addUserRide(id, ride);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", "Ride added successfully for user ID: " + id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> updateRide(@PathVariable Long id, @RequestBody Ride ride) {
        rideService.updateRide(id, ride);
        return ResponseEntity.ok(Map.of("message", "Ride updated successfully."));
    }
}
