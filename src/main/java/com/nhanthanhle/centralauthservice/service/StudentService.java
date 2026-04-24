package com.nhanthanhle.centralauthservice.service;

import com.nhanthanhle.centralauthservice.dto.request.StudentCreationRequest;
import com.nhanthanhle.centralauthservice.dto.request.StudentUpdateRequest;
import com.nhanthanhle.centralauthservice.dto.response.StudentResponse;
import com.nhanthanhle.centralauthservice.dto.response.UserResponse;
import com.nhanthanhle.centralauthservice.entity.Student;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StudentService {

   List<Student> getAll();

   Student getStudent(String studentId);


   void deleteStudent(String id);

   Student createStudent(StudentCreationRequest request);

   StudentResponse updateStudent(String id, StudentUpdateRequest request);




}
