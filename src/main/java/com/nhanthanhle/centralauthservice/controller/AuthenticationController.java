package com.nhanthanhle.centralauthservice.controller;

import com.nhanthanhle.centralauthservice.dto.request.ApiResponse;
import com.nhanthanhle.centralauthservice.dto.request.AuthenticationRequest;
import com.nhanthanhle.centralauthservice.dto.response.AuthenticationResponse;
import com.nhanthanhle.centralauthservice.service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.json.JsonMapper;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;


    @PostMapping("/log-in")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        ApiResponse<AuthenticationResponse> apiResponse = new ApiResponse();
        boolean res = authenticationService.authenticate(request);

//        return ApiResponse.<AuthenticationResponse>builder()
//                .result(
//                        AuthenticationResponse.builder()
//                                .authenticated(res)
//                                .build()
//                )
//                .build();
        AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .authenticated(res)
                .build();

        apiResponse.setResult(authenticationResponse);
        return apiResponse;
    }
}
