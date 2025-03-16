package com.oyetaxi.User.Details.repository;

import com.oyetaxi.User.Details.entity.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RideRepo extends JpaRepository<Ride, Long> {
    // Find rides by passenger ID
    @Query("SELECT r FROM Ride r WHERE r.passenger.id = :passengerId")
    List<Ride> findByPassengerId(@Param("passengerId") Long passengerId);

    // Find rides by driver ID
    @Query("SELECT r FROM Ride r WHERE r.driver.id = :driverId")
    List<Ride> findByDriverId(@Param("driverId") Long driverId);
}
