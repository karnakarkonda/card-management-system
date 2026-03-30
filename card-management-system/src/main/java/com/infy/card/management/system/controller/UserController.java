package com.infy.card.management.system.controller;


import com.infy.card.management.system.Model.User;
import com.infy.card.management.system.dto.UserRequest;
import com.infy.card.management.system.dto.UserResponse;
import com.infy.card.management.system.response.ApiResponse;
import com.infy.card.management.system.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/user")
@Slf4j
public class UserController {

    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }


    @PostMapping("/save")
    public ResponseEntity<ApiResponse> save(@Valid @RequestBody UserRequest request) {
        log.info("POST /v1/user/save called for phone number: {}", request.getPhoneNumber());
        User savedUser = userService.saveUser(request);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("User saved successfully with userId: " + savedUser.getUserId());
        apiResponse.setData(modelMapper.map(savedUser, UserResponse.class));
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Integer userId) {
        log.info("GET /v1/user/{} called for fetching user details", userId);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setMessage("User details fetched successfully for userId: " + userId);
        apiResponse.setData(userService.getUserById(userId));
        return ResponseEntity.ok(apiResponse);


    }
}
