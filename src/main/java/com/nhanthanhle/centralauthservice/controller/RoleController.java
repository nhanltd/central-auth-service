package com.nhanthanhle.centralauthservice.controller;

import com.nhanthanhle.centralauthservice.dto.request.ApiResponse;
import com.nhanthanhle.centralauthservice.dto.request.RoleRequest;
import com.nhanthanhle.centralauthservice.dto.response.RoleResponse;
import com.nhanthanhle.centralauthservice.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/roles")
@RestController
@FieldDefaults(makeFinal = true)
public class RoleController {

    RoleService roleService;
    @PostMapping
    ApiResponse<RoleResponse> create(@RequestBody RoleRequest request) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.create(request))
                .build();
    }
    @GetMapping
    ApiResponse<List<RoleResponse>> getAll() {
        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAll())
                .build();
    }

    @DeleteMapping("/{role}")
    ApiResponse<Void> delete(@PathVariable("role") String role) {
        roleService.delete(role);
        return ApiResponse.<Void>builder().build();
    }
}
