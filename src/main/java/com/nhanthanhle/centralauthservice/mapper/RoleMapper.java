package com.nhanthanhle.centralauthservice.mapper;
import com.nhanthanhle.centralauthservice.dto.request.RoleRequest;
import com.nhanthanhle.centralauthservice.dto.response.RoleResponse;
import com.nhanthanhle.centralauthservice.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);
    RoleResponse toRoleResponse(Role role);
}
