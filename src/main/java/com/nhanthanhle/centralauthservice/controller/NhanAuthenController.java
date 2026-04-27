package com.nhanthanhle.centralauthservice.controller;

import com.nhanthanhle.centralauthservice.dto.request.ApiResponse;
import com.nhanthanhle.centralauthservice.dto.request.NhanAuthenRequest;
import com.nhanthanhle.centralauthservice.dto.response.NhanAuthenResponse;
import com.nhanthanhle.centralauthservice.service.NhanAuthenService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nhan")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class NhanAuthenController {

    NhanAuthenService authenService;
    @PostMapping("/log-in")
    public ApiResponse<NhanAuthenResponse> login(@RequestBody NhanAuthenRequest request){

        ApiResponse apiResponse = new ApiResponse();
        boolean res = authenService.authenticated(request);
        NhanAuthenResponse authenResponse = NhanAuthenResponse.builder()
                .authenticated(res)
                .build();
        apiResponse.setMessage("WELCOME TO THANH NHAN AUTHENTICATION");
        apiResponse.setResult(authenResponse);
        return apiResponse;
    }
}
