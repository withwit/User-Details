package com.oyetaxi.User.Details.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oyetaxi.User.Details.entity.Ride;
import com.oyetaxi.User.Details.exception.ResourceNotFoundException;
import com.oyetaxi.User.Details.service.RideService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RideController.class)
@ExtendWith(MockitoExtension.class)
class RideControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RideService rideService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testGetRideById_Success() throws Exception {
        // 🎯 Arrange
        Ride ride = new Ride(List.of("{Lat: 124.23, Lon: 12.55}"), "100", "10 km");
        ride.setId(1L);

        //Direct the rideService mock
        Mockito.when(rideService.getRide(1L)).thenReturn(ride);

        // 🚀 Act & Assert
        mockMvc.perform(get("/ride/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.priceEstimate").value("100"))
                .andExpect(jsonPath("$.distance").value("10 km"));
    }

    @Test
    void testGetRideById_NotFound() throws Exception {
        Mockito.when(rideService.getRide(1L)).thenThrow(new ResourceNotFoundException("Ride not found"));

        mockMvc.perform(get("/ride/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddUserRide_Success() throws Exception {
        Ride ride = new Ride(List.of("Start", "End"), "150", "15 km");

        // We did not mock rideService because we are not actually adding the ride
        // This controller just test the response and not addUserRide()
        mockMvc.perform(post("/ride/add/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ride)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Ride added successfully for user ID: 1"));

        // ✅ Verify the service method was called once
        // Since above we just tested the response that rideService returns and not actually
        // addUserRide, so we just check if the method was called once or not
        verify(rideService, times(1)).addUserRide(eq(1L), any(Ride.class));
    }

    @Test
    void testAddUserRide_Failed() throws Exception {
        Mockito.doThrow(new ResourceNotFoundException("Ride not found with ID: " + 12121L))
                .when(rideService).addUserRide(eq(12121L), any(Ride.class));

        Ride ride = new Ride(List.of("{}"), "10", "112");

        // perform post request
        mockMvc.perform(post("/ride/add/12121")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ride)))
                .andExpect(status().isNotFound())  // Check 404
                .andExpect(result -> {
                    Exception exception = result.getResolvedException();
                    assertNotNull(exception);
                    assertTrue(exception instanceof ResourceNotFoundException);
                    assertEquals("Ride not found with ID: 12121", exception.getMessage());
                });


    }


    @Test
    void testUpdateRide_Success() throws Exception {
        Ride ride = new Ride(List.of("{}"), "121", "11");

        mockMvc.perform(put("/ride/11")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ride)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Ride updated successfully."));

        verify(rideService, times(1)).updateRide(eq(11L), any(Ride.class));
    }
}
