package com.nhanthanhle.centralauthservice.mapper;

import com.nhanthanhle.centralauthservice.dto.request.UserCreationRequest;
import com.nhanthanhle.centralauthservice.dto.request.UserUpdateRequest;
import com.nhanthanhle.centralauthservice.dto.response.UserResponse;
import com.nhanthanhle.centralauthservice.entity.User;
import com.nhanthanhle.centralauthservice.repository.UserRepository;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);
    UserResponse toUserResponse(User user);
    void updateUser(UserUpdateRequest request, @MappingTarget User user);
}
