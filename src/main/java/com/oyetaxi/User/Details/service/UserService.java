package com.oyetaxi.User.Details.service;

import com.oyetaxi.User.Details.config.SecurityConfig;
import com.oyetaxi.User.Details.dto.UserDataDTO;
import com.oyetaxi.User.Details.entity.Driver;
import com.oyetaxi.User.Details.entity.Ride;
import com.oyetaxi.User.Details.entity.User;
import com.oyetaxi.User.Details.exception.InvalidRequestException;
import com.oyetaxi.User.Details.exception.ResourceNotFoundException;
import com.oyetaxi.User.Details.factory.UserFactory;
import com.oyetaxi.User.Details.misc.UserType;
import com.oyetaxi.User.Details.repository.RideRepo;
import com.oyetaxi.User.Details.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    RideRepo rideRepo;
    @Autowired
    UserFactory userFactory;

//    @Autowired
//    SecurityConfig securityConfig;


    public User createUser(UserDataDTO dataDTO) {
        if (dataDTO == null || dataDTO.getUser() == null) {
            throw new InvalidRequestException("User data cannot be null");
        }

        // ✅ Hash the password before setting it in the entity
//        String hashedPassword = securityConfig.passwordEncoder(dataDTO.getUser().getPassword());
//        dataDTO.getUser().setPassword(hashedPassword);

        User user = userFactory.createUser(dataDTO);

        return userRepo.save(user);
    }

    public User getUserById(Long id) {
        return userRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
    }

    @Transactional
    public User updateUser(Long id, UserDataDTO dataDTO) {
        User _user = getUserById(id);

        if (dataDTO == null || dataDTO.getUser() == null) {
            throw new InvalidRequestException("Update data cannot be null");
        }

        User userData = dataDTO.getUser();
        _user.setUpdateDt(LocalDateTime.now());
        _user.setName(userData.getName());
        _user.setCurrentLoc(userData.getCurrentLoc());
        _user.setEmail(userData.getEmail());

        if (_user instanceof Driver driver) {
            driver.setLicenseNumber(dataDTO.getLicenceNumber());
            driver.setRating(dataDTO.getRating());
        }
        return userRepo.save(_user);
    }

    public Boolean deleteUser(Long id) {
        getUserById(id);  // If user is not found, exception is thrown
        userRepo.deleteById(id);
        return Boolean.TRUE;
    }

    public List<Ride> getUserRides(Long id) {
        User user = getUserById(id);

        if (user.getUserType() == UserType.PASSENGER) {
            return rideRepo.findByPassengerId(id);
        } else if (user.getUserType() == UserType.DRIVER) {
            return rideRepo.findByDriverId(id);
        }

        throw new ResourceNotFoundException("No rides found for user with ID: " + id);
    }

    @Transactional
    public void addUserRide(Long id, Ride _ride) {
        User user = getUserById(id);

        if (_ride == null || _ride.getLocationData() == null || _ride.getLocationData().isEmpty()) {
            throw new InvalidRequestException("Ride details are incomplete");
        }

        Ride ride = new Ride(_ride.getLocationData(), _ride.getPriceEstimate(), _ride.getDistance());

        if (user.getUserType().equals(UserType.PASSENGER)) {
            ride.setPassenger(user);
        } else {
            ride.setDriver(user);
        }

        rideRepo.save(ride);
    }
}
