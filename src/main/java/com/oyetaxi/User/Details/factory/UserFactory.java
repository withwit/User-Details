package com.oyetaxi.User.Details.factory;

import com.oyetaxi.User.Details.dto.UserDataDTO;
import com.oyetaxi.User.Details.entity.Driver;
import com.oyetaxi.User.Details.entity.Passenger;
import com.oyetaxi.User.Details.entity.User;
import com.oyetaxi.User.Details.misc.UserType;
import org.springframework.stereotype.Component;

@Component
public class UserFactory {
    public User createUser(UserDataDTO dataDTO) {
        if (UserType.PASSENGER.equals(dataDTO.getUser().getUserType())) {
            return new Passenger(dataDTO);
        } else if (UserType.DRIVER.equals(dataDTO.getUser().getUserType())) {
            return new Driver(dataDTO);
        } else {
            throw new IllegalArgumentException("Invalid user type: " + dataDTO.getUser().getUserType());
        }
    }
}
