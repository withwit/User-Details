package com.oyetaxi.User.Details.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.oyetaxi.User.Details.dto.UserDataDTO;
import com.oyetaxi.User.Details.entity.User;
import com.oyetaxi.User.Details.factory.UserFactory;
import com.oyetaxi.User.Details.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @MockBean
    UserService userService;

    @Autowired
    MockMvc mockMvc;


    ObjectMapper objectMapper = new ObjectMapper();


    @Test
    void createUserTest_Success() throws Exception {
        String payload = "{\n" +
                "  \"user\": {\n" +
                "    \"type\": \"DRIVER\",\n" +
                "    \"name\": \"Anmol Sahu\",\n" +
                "    \"mobileNumber\": \"958963423\",\n" +
                "    \"email\": \"jane@examle.com\",\n" +
                "    \"currentLoc\": \"Jabalpur\"\n" +
                "  }\n" +
                "}\n";
        UserDataDTO dataDTO = objectMapper.readValue(payload, UserDataDTO.class);

        //Since User have a protected constructor, we cannot create User directly
        User mockUser = Mockito.mock(User.class);


        System.out.println("DataDTO:  " + objectMapper.writeValueAsString(dataDTO));

        MvcResult result = mockMvc.perform(
                        post("/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dataDTO)))
                .andExpect(status().isCreated())
                .andReturn();

        verify(userService, times(1)).createUser(any(UserDataDTO.class));


    }

    @Test
    void createUserTest_Failed(){

    }
}
