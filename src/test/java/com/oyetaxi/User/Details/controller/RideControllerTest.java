package com.oyetaxi.User.Details.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oyetaxi.User.Details.entity.Ride;
import com.oyetaxi.User.Details.exception.ResourceNotFoundException;
import com.oyetaxi.User.Details.service.RideService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RideControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private RideService rideService;  // Mocking service layer

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testGetRideById_Success() throws Exception {
        // 🎯 Arrange
        Ride ride = new Ride(List.of("Point A", "Point B"), "100", "10 km");
        ride.setId(1L);

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

        mockMvc.perform(post("/ride/add/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ride)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("Ride added successfully for user ID: 1"));
    }

    @Test
    void testUpdateRide_Success() throws Exception {
        Ride ride = new Ride(List.of("New Location"), "200", "20 km");

        mockMvc.perform(put("/ride/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ride)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Ride updated successfully."));
    }
}
