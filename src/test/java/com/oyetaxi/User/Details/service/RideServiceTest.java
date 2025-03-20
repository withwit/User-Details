package com.oyetaxi.User.Details.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oyetaxi.User.Details.dto.UserDataDTO;
import com.oyetaxi.User.Details.entity.Ride;
import com.oyetaxi.User.Details.entity.User;
import com.oyetaxi.User.Details.factory.UserFactory;
import com.oyetaxi.User.Details.misc.UserType;
import com.oyetaxi.User.Details.repository.RideRepo;
import com.oyetaxi.User.Details.repository.UserRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ActiveProfiles("test")
@Testcontainers
class RideServiceTest {
    @Autowired
    private RideService rideService;

    @Autowired
    private RideRepo rideRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserFactory userFactory;

    ObjectMapper objectMapper = new ObjectMapper();

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15").withDatabaseName("testdb").withUsername("test").withPassword("test");

    @BeforeAll
    static void setup() {
        postgres.start();
        System.setProperty("spring.datasource.url", postgres.getJdbcUrl());
        System.setProperty("spring.datasource.username", postgres.getUsername());
        System.setProperty("spring.datasource.password", postgres.getPassword());
    }

    @BeforeEach
    void cleanDatabase() {
        rideRepo.deleteAll();
        userRepo.deleteAll();
    }

    @Test
    void testAddAndRetrieveRides() throws Exception {
        // Create and save a user
        String payload = "{\n" +
                "  \"user\": {\n" +
                "    \"type\": \"PASSENGER\",\n" +
                "    \"name\": \"Anmol Sahu\",\n" +
                "    \"mobileNumber\": \"958963423\",\n" +
                "    \"email\": \"jane@examle.com\",\n" +
                "    \"currentLoc\": \"Jabalpur\"\n" +
                "  }\n" +
                "}\n";

        UserDataDTO dataDTO = objectMapper.readValue(payload, UserDataDTO.class);

        User passenger = userFactory.createUser(dataDTO);
        userRepo.save(passenger);

        // Create a ride
        Ride ride = new Ride(List.of("{Jabalpur}"), "100", "10.5");
        rideService.addUserRide(passenger.getId(), ride);

        // Retrieve rides
        List<Ride> rides = rideService.getUserRides(passenger.getId());

        assertEquals(1, rides.size());
        assertEquals(ride.getLocationData(), rides.get(0).getLocationData());
    }
}
