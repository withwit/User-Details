package com.oyetaxi.User.Details.entity;

import com.oyetaxi.User.Details.dto.UserDataDTO;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Driver extends User {
    private String licenseNumber;
    private String rating;

    public Driver(UserDataDTO dataDTO) {
        super(dataDTO.getUser());
        this.licenseNumber=dataDTO.getLicenceNumber();
        this.rating=dataDTO.getRating();
    }
}
