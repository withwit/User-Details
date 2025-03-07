package com.oyetaxi.User.Details.factory;

import com.oyetaxi.User.Details.entity.Driver;
import com.oyetaxi.User.Details.entity.Passenger;
import com.oyetaxi.User.Details.entity.User;
import com.oyetaxi.User.Details.entity.UserInterface;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {
    public UserInterface createUser(User user) {
        if ("PASSENGER".equalsIgnoreCase(user.getUserType())) {
            return new Passenger(user);
        } else if ("DRIVER".equalsIgnoreCase(user.getUserType())) {
            return new Driver(user);
        } else {
            throw new IllegalArgumentException("Invalid user type: " + user.getUserType());
        }
    }
}
