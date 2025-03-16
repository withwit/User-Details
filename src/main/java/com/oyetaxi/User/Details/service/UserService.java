package com.oyetaxi.User.Details.service;

import com.oyetaxi.User.Details.dto.UserDataDTO;
import com.oyetaxi.User.Details.entity.Driver;
import com.oyetaxi.User.Details.entity.Ride;
import com.oyetaxi.User.Details.entity.User;
import com.oyetaxi.User.Details.factory.UserFactory;
import com.oyetaxi.User.Details.misc.UserType;
import com.oyetaxi.User.Details.repository.RideRepo;
import com.oyetaxi.User.Details.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    RideRepo rideRepo;
    @Autowired
    UserFactory userFactory;

    public User createUser(UserDataDTO dataDTO) {
        User user = userFactory.createUser(dataDTO);
        userRepo.save(user);
        return user;
    }

    public User getUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public User updateUser(Long id, UserDataDTO dataDTO) {
        User _user = getUserById(id);
        User userData = dataDTO.getUser();

        _user.setUpdateDt(LocalDateTime.now());
        _user.setName(userData.getName());
        _user.setCurrentLoc(userData.getCurrentLoc());
        _user.setEmail(userData.getEmail());

        //If the user is a driver, update additional fields
        if (_user instanceof Driver driver) {
            driver.setLicenseNumber(dataDTO.getLicenceNumber());
            driver.setRating(dataDTO.getRating());
        }
        userRepo.save(_user);
        System.out.println(_user);
        return _user;
    }

    public void deleteUser(Long id) {
        getUserById(id);
        userRepo.deleteById(id);
        System.out.println(id + "user deleted.");
    }

    public List<Ride> getUserRides(Long id) {
        User user = getUserById(id);
        if (user.getUserType() == UserType.PASSENGER) {
            return rideRepo.findByPassengerId(id);
        } else if (user.getUserType() == UserType.DRIVER) {
            return rideRepo.findByDriverId(id);
        }

        new RuntimeException("Ride not found");
        return new ArrayList<Ride>();
    }

    @Transactional
    public void addUserRide(Long id, Ride _ride) {
        User user = getUserById(id);
        System.out.println(_ride.toString());

        Ride ride = new Ride(_ride.getLocationData(), _ride.getPriceEstimate(), _ride.getDistance());

        if (user.getUserType().equals(UserType.PASSENGER)) {
            ride.setPassenger(user);
        } else {
            ride.setDriver(user);
        }

        rideRepo.save(ride);
    }

}
