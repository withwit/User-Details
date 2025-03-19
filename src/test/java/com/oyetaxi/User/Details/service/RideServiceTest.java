package com.oyetaxi.User.Details.service;

import com.oyetaxi.User.Details.dto.UserDataDTO;
import com.oyetaxi.User.Details.entity.Driver;
import com.oyetaxi.User.Details.entity.Passenger;
import com.oyetaxi.User.Details.entity.Ride;
import com.oyetaxi.User.Details.entity.User;
import com.oyetaxi.User.Details.exception.ResourceNotFoundException;
import com.oyetaxi.User.Details.factory.UserFactory;
import com.oyetaxi.User.Details.misc.UserType;
import com.oyetaxi.User.Details.repository.RideRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class RideServiceTest {

    @MockBean
    private RideService rideService; // Service under test

    @Mock
    private RideRepo rideRepo; // Mocked repository

    @Mock
    private UserService userService; // Mocked user service

    @Mock
    private UserFactory userFactory; // Mocked user factory

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    // ✅ Test: Get rides for a passenger
    @Test
    void testGetUserRides_Passenger() {
        // 🏗️ Arrange
        Long userId = 1L;
        UserDataDTO dataDTO = new UserDataDTO(); // Mock DTO

        // ✅ Mock UserFactory to return a Passenger
        User mockUser = new Passenger(dataDTO);
        Mockito.when(userFactory.createUser(Mockito.any(UserDataDTO.class))).thenReturn(mockUser);

        // ✅ Ensure UserService returns the created user
        Mockito.when(userService.getUserById(userId)).thenReturn(mockUser);

        Ride ride1 = new Ride(List.of("Loc1", "Loc2"), "500", "10km");
        ride1.setPassenger(mockUser);
        Ride ride2 = new Ride(List.of("Loc3", "Loc4"), "300", "5km");
        ride2.setPassenger(mockUser);
        List<Ride> mockRides = List.of(ride1, ride2);

        Mockito.when(rideRepo.findByPassengerId(userId)).thenReturn(mockRides);

        // 🎯 Act
        List<Ride> rides = rideService.getUserRides(userId);

        // ✅ Assert
        assertNotNull(rides);
        assertEquals(2, rides.size());
        assertEquals("500", rides.get(0).getPriceEstimate());

        Mockito.verify(rideRepo, times(1)).findByPassengerId(userId);
    }

    // ✅ Test: Get rides for a driver
    @Test
    void testGetUserRides_Driver() {
        // 🏗️ Arrange
        Long userId = 2L;
        User mockUser = new Driver(new UserDataDTO());
        mockUser.setId(userId);
        mockUser.setType(UserType.DRIVER);

        Ride ride1 = new Ride(List.of("Loc5", "Loc6"), "600", "12km");
        ride1.setDriver(mockUser);

        Mockito.when(userService.getUserById(userId)).thenReturn(mockUser);
        Mockito.when(rideRepo.findByDriverId(userId)).thenReturn(List.of(ride1));

        // 🎯 Act
        List<Ride> rides = rideService.getUserRides(userId);

        // ✅ Assert
        assertNotNull(rides);
        assertEquals(1, rides.size());
        assertEquals("600", rides.get(0).getPriceEstimate());

        Mockito.verify(rideRepo, times(1)).findByDriverId(userId);
    }

    // ❌ Test: Get rides for an invalid user (exception case)
    @Test
    void testGetUserRides_InvalidUser()     {
        // 🏗️ Arrange
        Long userId = 99L;
        UserDataDTO dataDTO = new UserDataDTO(); // Mock input DTO

        // Mock factory to return a user with no valid type
        User mockUser = Mockito.mock(User.class);
        Mockito.when(mockUser.getUserType()).thenReturn(null); // Invalid user type

        Mockito.when(userService.getUserById(userId)).thenReturn(mockUser);

        // 🚨 Act & Assert (Expect Exception)
        assertThrows(ResourceNotFoundException.class, () -> rideService.getUserRides(userId));

        // ✅ Verify interactions
        Mockito.verify(userService, times(1)).getUserById(userId);
    }

    // ✅ Test: Add ride for a passenger
    @Test
    void testAddUserRide_Passenger() {
        // 🏗️ Arrange
        Long userId = 3L;
        UserDataDTO dataDTO = new UserDataDTO();
        User mockUser = new Passenger(dataDTO);
        mockUser.setId(userId);
        mockUser.setType(UserType.PASSENGER);

        Ride ride = new Ride(List.of("LocX", "LocY"), "700", "15km");

        Mockito.when(userService.getUserById(userId)).thenReturn(mockUser);

        // 🎯 Act
        rideService.addUserRide(userId, ride);

        // ✅ Assert
        assertEquals(mockUser, ride.getPassenger());
        Mockito.verify(rideRepo, times(1)).save(ride);
    }

    // ✅ Test: Add ride for a driver
    @Test
    void testAddUserRide_Driver() {
        // 🏗️ Arrange
        Long userId = 4L;
        UserDataDTO dataDTO = new UserDataDTO();
        User mockUser = new Driver(dataDTO);
        mockUser.setId(userId);
        mockUser.setType(UserType.DRIVER);

        Ride ride = new Ride(List.of("LocA", "LocB"), "400", "8km");

        Mockito.when(userService.getUserById(userId)).thenReturn(mockUser);

        // 🎯 Act
        rideService.addUserRide(userId, ride);

        // ✅ Assert
        assertEquals(mockUser, ride.getDriver());
        Mockito.verify(rideRepo, times(1)).save(ride);
    }

    // ❌ Test: Add ride for an invalid user
    @Test
    void testAddUserRide_InvalidUser() {
        Long userId = 99L;
        Ride ride = new Ride(List.of("LocX"), "200", "3km");

        Mockito.when(userService.getUserById(userId)).thenThrow(new ResourceNotFoundException("User not found"));

        // 🚨 Act & Assert (Expect Exception)
        assertThrows(ResourceNotFoundException.class, () -> rideService.addUserRide(userId, ride));
    }

    // ✅ Test: Get ride by ID
    @Test
    void testGetRide_Success() {
        Long rideId = 1L;
        Ride ride = new Ride(List.of("Start", "End"), "1000", "20km");
        ride.setId(rideId);

        Mockito.when(rideRepo.findById(rideId)).thenReturn(Optional.of(ride));

        // 🎯 Act
        Ride foundRide = rideService.getRide(rideId);

        // ✅ Assert
        assertNotNull(foundRide);
        assertEquals("1000", foundRide.getPriceEstimate());

        Mockito.verify(rideRepo, times(1)).findById(rideId);
    }

    // ❌ Test: Get ride by ID - Not Found
    @Test
    void testGetRide_NotFound() {
        Long rideId = 99L;
        Mockito.when(rideRepo.findById(rideId)).thenReturn(Optional.empty());

        // 🚨 Act & Assert (Expect Exception)
        assertThrows(ResourceNotFoundException.class, () -> rideService.getRide(rideId));
    }

    // ✅ Test: Update ride
    @Test
    void testUpdateRide_Success() {
        // 🏗️ Arrange
        Long rideId = 1L;
        Ride existingRide = new Ride(List.of("OldLoc"), "500", "10km");
        existingRide.setId(rideId);

        Ride updatedRide = new Ride(List.of("NewLoc"), "800", "15km");

        Mockito.when(rideRepo.findById(rideId)).thenReturn(Optional.of(existingRide));

        // 🎯 Act
        rideService.updateRide(rideId, updatedRide);

        // ✅ Assert
        assertEquals("800", existingRide.getPriceEstimate());
        assertEquals("15km", existingRide.getDistance());
        assertEquals(List.of("NewLoc"), existingRide.getLocationData());
    }

    // ❌ Test: Update ride - Not Found
    @Test
    void testUpdateRide_NotFound() {
        Long rideId = 99L;
        Ride updatedRide = new Ride(List.of("NewLoc"), "800", "15km");

        Mockito.when(rideRepo.findById(rideId)).thenReturn(Optional.empty());

        // 🚨 Act & Assert (Expect Exception)
        assertThrows(ResourceNotFoundException.class, () -> rideService.updateRide(rideId, updatedRide));
    }
}
