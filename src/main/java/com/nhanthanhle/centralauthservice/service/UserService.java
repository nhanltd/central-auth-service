package com.nhanthanhle.centralauthservice.service;

import com.nhanthanhle.centralauthservice.dto.request.UserCreationRequest;
import com.nhanthanhle.centralauthservice.dto.request.UserUpdateRequest;
import com.nhanthanhle.centralauthservice.entity.User;
import com.nhanthanhle.centralauthservice.exception.AppException;
import com.nhanthanhle.centralauthservice.exception.ErrorCode;
import com.nhanthanhle.centralauthservice.mapper.UserMapper;
import com.nhanthanhle.centralauthservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private final UserMapper userMapper;
    public User createUserRequest(UserCreationRequest userCreationRequest) {
        if (userRepository.existsByUsername(userCreationRequest.getUsername())) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        User user = userMapper.toUser(userCreationRequest);


        return userRepository.save(user);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public User getUser(String id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(String id, UserUpdateRequest request) {

        if (!userRepository.existsById(id)) { /// false -> thr
            throw new RuntimeException("Dont have user in db");
        }
        User user = getUser(id);
        userMapper.updateUser(request, user);

        System.out.println(user.getPassword());
        System.out.println(user.getLastname());
        System.out.println(user.getFirstname());
        System.out.println(user.getDob());

        return userRepository.save(user);
    }

    public void deleteUser(String id) {
        if (!userRepository.existsById(id)) {
            throw new RuntimeException("USER NOT EXISTED");
        }
        userRepository.deleteById(id);
    }
}
