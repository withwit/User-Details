package com.oyetaxi.User.Details.service;

import com.oyetaxi.User.Details.entity.Ride;
import com.oyetaxi.User.Details.entity.User;
import com.oyetaxi.User.Details.entity.UserInterface;
import com.oyetaxi.User.Details.factory.UserFactory;
import com.oyetaxi.User.Details.repository.RideRepo;
import com.oyetaxi.User.Details.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    RideRepo rideRepo;
    @Autowired
    UserFactory userFactory;

    public UserInterface createUser(
            String name, String type, String mobileNumber, String email, String currentLoc) {
        User user = new User(name, type, mobileNumber, email, currentLoc);
        return userFactory.createUser(userRepo.save(user));
    }

    public User getUserById(Long id) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return user;
    }

    @Transactional
    public User updateUser(String id, User user) {
        user.setUpdateDt(LocalDateTime.now());
        userRepo.save(user);
        return user;
    }

    public void deleteUser(Long id) {
        userRepo.deleteById(id);
        System.out.println(id + "user deleted.");
    }

    public List<Ride> getUserRides(Long id) {
        Optional<User> user = userRepo.findById(id);
        if (user.isPresent()) {
            return user.get().getRides();
        }

        return user.get().getRides();
    }

    @Transactional
    public Void addUserRide(Long id, Ride ride) {
        Optional<User> user = userRepo.findById(id);
        if (!user.isPresent()) {
            throw new RuntimeException("User not found");
        }
        user.get().setRides(ride);
        return null;
    }


}
