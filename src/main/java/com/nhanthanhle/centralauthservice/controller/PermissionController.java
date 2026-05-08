package com.nhanthanhle.centralauthservice.controller;

import com.nhanthanhle.centralauthservice.dto.request.ApiResponse;
import com.nhanthanhle.centralauthservice.dto.request.PermissionRequest;
import com.nhanthanhle.centralauthservice.dto.response.PermissionResponse;
import com.nhanthanhle.centralauthservice.service.PermissionService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/permissions")
@RestController
@FieldDefaults(makeFinal = true)
public class PermissionController {

    PermissionService permissionService;
    @PostMapping()
    public ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest request) {
        return ApiResponse.<PermissionResponse>builder()
                .result(permissionService.create(request))
                .build();
    }
    @GetMapping
    public ApiResponse<List<PermissionResponse>> getAll() {
        return ApiResponse.<List<PermissionResponse>>builder()
                .result(permissionService.getAll())
                .build();
    }

    @DeleteMapping("/{permissionId}")
    public ApiResponse<Void> delete(@PathVariable("permissionId") String permissionId) {
        permissionService.delete(permissionId);
        return ApiResponse.<Void>builder().build();
    }
}
