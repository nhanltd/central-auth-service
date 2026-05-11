package com.nhanthanhle.centralauthservice.mapper;

import com.nhanthanhle.centralauthservice.dto.request.UserCreationRequest;
import com.nhanthanhle.centralauthservice.dto.request.UserUpdateRequest;
import com.nhanthanhle.centralauthservice.dto.response.UserResponse;
import com.nhanthanhle.centralauthservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    UserResponse toUserResponse(User user);
    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
