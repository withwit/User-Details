package com.oyetaxi.User.Details.entity;

import com.oyetaxi.User.Details.dto.UserDataDTO;
import jakarta.persistence.Entity;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Passenger extends User {

    public Passenger(UserDataDTO dataDTO) {
        super(dataDTO.getUser());
    }

}
