package com.oyetaxi.User.Details.repository;

import com.oyetaxi.User.Details.entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRepo extends JpaRepository<Ride, Long> {

}
