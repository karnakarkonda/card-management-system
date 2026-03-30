package com.infy.card.management.system.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.infy.card.management.system.Model.User;
import com.infy.card.management.system.dto.UserRequest;
import com.infy.card.management.system.dto.UserResponse;
import com.infy.card.management.system.service.UserService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc(addFilters = false)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @MockitoBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private ModelMapper modelMapper;

    @Test
    void shouldSaveUserSuccessfuly() throws Exception {

        UserRequest userRequest = new UserRequest();
        userRequest.setName("John Doe");
        userRequest.setPassword("Password123");
        userRequest.setPhoneNumber("1234567890");

        User savedUser = new User();
        savedUser.setUserId(1);
        savedUser.setName("John Doe");
        savedUser.setPhoneNumber("1234567890");
        savedUser.setPassword("Password123");

        UserResponse userResponse = new UserResponse();
        userResponse.setUserId(1);
        userResponse.setName("John Doe");
        userResponse.setPhoneNumber(Long.valueOf("1234567890"));



        // Mock the userService to return the saved user when saveUser is called
        when(userService.saveUser(any(UserRequest.class))).thenReturn(savedUser);

        // Mock the modelMapper to return a UserResponse when mapping the saved user
        when(modelMapper.map(any(User.class), any(Class.class))).thenReturn(userResponse);

        mockMvc.perform(post("/v1/user/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value("User saved successfully with userId: 1"))
                .andExpect(jsonPath("$.data.userId").value(1))
                .andExpect(jsonPath("$.data.name").value("John Doe"))
                .andExpect(jsonPath("$.data.phoneNumber").value(1234567890L));
    }

    @Test
    void getUserById() {
    }
}