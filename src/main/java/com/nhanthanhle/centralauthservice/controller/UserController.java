package com.nhanthanhle.centralauthservice.controller;

import com.nhanthanhle.centralauthservice.dto.request.ApiResponse;
import com.nhanthanhle.centralauthservice.dto.request.UserCreationRequest;
import com.nhanthanhle.centralauthservice.dto.request.UserUpdateRequest;
import com.nhanthanhle.centralauthservice.dto.response.UserResponse;
import com.nhanthanhle.centralauthservice.entity.User;
import com.nhanthanhle.centralauthservice.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "API FOR USERS", description = "CRUD USER Create Read Update Delete")
public class UserController {
    private final UserService userService;

    @PostMapping()
    public ApiResponse<User> createUser(@Valid @RequestBody UserCreationRequest request) {
        ApiResponse<User> apiResponse = new ApiResponse<>();
        apiResponse.setResult(userService.createUserRequest(request));
        return apiResponse;
    }

    @GetMapping()
    public List<User> getUsers() {
        return userService.getUsers();
    }


    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable("userId") String userId) {
        return userService.getUser(userId);
    }

    @PutMapping("/{userId}")
    public UserResponse updateUser(@PathVariable("userId") String userId, @RequestBody UserUpdateRequest request) {
        return userService.updateUser(userId, request);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable("userId") String userId) {
        userService.deleteUser(userId);
    }
}
