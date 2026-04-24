package com.nhanthanhle.centralauthservice.mapper;

import com.nhanthanhle.centralauthservice.dto.request.StudentCreationRequest;
import com.nhanthanhle.centralauthservice.dto.request.StudentUpdateRequest;
import com.nhanthanhle.centralauthservice.dto.response.StudentResponse;
import com.nhanthanhle.centralauthservice.dto.response.UserResponse;
import com.nhanthanhle.centralauthservice.entity.Student;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    Student toStudent(StudentCreationRequest request);
    StudentResponse toStudentResponse(StudentUpdateRequest request, @MappingTarget Student student);
}
