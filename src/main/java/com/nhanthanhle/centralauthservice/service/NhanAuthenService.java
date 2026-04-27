package com.nhanthanhle.centralauthservice.service;

import com.nhanthanhle.centralauthservice.dto.request.NhanAuthenRequest;
import com.nhanthanhle.centralauthservice.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class NhanAuthenService {
    UserRepository userRepository;
    public boolean authenticated(NhanAuthenRequest request) {
        var user = userRepository.findUserByUsername(request.getUsername()).orElseThrow(() -> new RuntimeException("Dont have user find by username"));
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);


        return passwordEncoder.matches(request.getPassword(), user.getPassword());






    }

}
