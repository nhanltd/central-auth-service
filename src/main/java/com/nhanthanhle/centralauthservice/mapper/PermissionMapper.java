package com.nhanthanhle.centralauthservice.mapper;

import com.nhanthanhle.centralauthservice.dto.request.PermissionRequest;
import com.nhanthanhle.centralauthservice.dto.response.PermissionResponse;
import com.nhanthanhle.centralauthservice.entity.Permission;
import com.nhanthanhle.centralauthservice.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}
