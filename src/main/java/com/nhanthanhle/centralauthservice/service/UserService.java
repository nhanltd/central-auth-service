package com.nhanthanhle.centralauthservice.service;

import com.nhanthanhle.centralauthservice.dto.request.UserCreationRequest;
import com.nhanthanhle.centralauthservice.dto.request.UserUpdateRequest;
import com.nhanthanhle.centralauthservice.entity.User;
import com.nhanthanhle.centralauthservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUserRequest(UserCreationRequest userCreationRequest) {
        User user = new User();

        user.setUsername(userCreationRequest.getUsername());
        user.setFirstname(userCreationRequest.getFirstname());
        user.setLastname(userCreationRequest.getLastname());
        user.setPassword(userCreationRequest.getPassword());
        user.setDob(userCreationRequest.getDob());

        return userRepository.save(user);


    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(String id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(String id, UserUpdateRequest request) {

        if (!userRepository.existsById(id)) {
            throw new RuntimeException("Dont have user in db");
        }
        User user = getUser(id);
        user.setPassword(request.getPassword());
        user.setDob(request.getDob());
        user.setLastname(request.getLastname());
        user.setFirstname(request.getFirstname());

        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("USER NOT EXISTED");
        }
        userRepository.deleteById(id);
    }
}
