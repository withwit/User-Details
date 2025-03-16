package com.oyetaxi.User.Details.service;

import com.oyetaxi.User.Details.entity.Ride;
import com.oyetaxi.User.Details.entity.User;
import com.oyetaxi.User.Details.exception.ResourceNotFoundException;
import com.oyetaxi.User.Details.misc.UserType;
import com.oyetaxi.User.Details.repository.RideRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RideService {

    @Autowired
    RideRepo rideRepo;

    @Autowired
    UserService userService;

    public List<Ride> getUserRides(Long id) {
        User user = userService.getUserById(id);
        if (user.getUserType() == UserType.PASSENGER) {
            return rideRepo.findByPassengerId(id);
        } else if (user.getUserType() == UserType.DRIVER) {
            return rideRepo.findByDriverId(id);
        }

        throw new ResourceNotFoundException("Rides not found for user ID: " + id);
    }

    @Transactional
    public void addUserRide(Long id, Ride _ride) {
        User user = userService.getUserById(id);
        System.out.println(_ride.toString());

        Ride ride = new Ride(_ride.getLocationData(), _ride.getPriceEstimate(), _ride.getDistance());

        if (user.getUserType().equals(UserType.PASSENGER)) {
            ride.setPassenger(user);
        } else {
            ride.setDriver(user);
        }

        rideRepo.save(ride);
    }

    public Ride getRide(Long id) {
        return rideRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ride not found with ID: " + id));
    }

    @Transactional
    public void updateRide(Long id, Ride _ride) {
        Ride ride = getRide(id);

        ride.setDriver(_ride.getDriver());
        ride.setPassenger(_ride.getPassenger());
        ride.setDistance(_ride.getDistance());
        ride.setPriceEstimate(_ride.getPriceEstimate());
        ride.setLocationData(_ride.getLocationData());
    }
}
