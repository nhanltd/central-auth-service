package com.nhanthanhle.centralauthservice.service.impl;

import com.nhanthanhle.centralauthservice.dto.request.StudentCreationRequest;
import com.nhanthanhle.centralauthservice.dto.request.StudentUpdateRequest;
import com.nhanthanhle.centralauthservice.dto.response.StudentResponse;
import com.nhanthanhle.centralauthservice.dto.response.UserResponse;
import com.nhanthanhle.centralauthservice.entity.Student;
import com.nhanthanhle.centralauthservice.mapper.StudentMapper;
import com.nhanthanhle.centralauthservice.repository.StudentRepository;
import com.nhanthanhle.centralauthservice.service.StudentService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StudentServiceImpl implements StudentService {
    final StudentRepository studentRepository;
    final StudentMapper studentMapper;
    @Override
    public List<Student> getAll() {
        return List.of();
    }

    @Override
    public Student getStudent(String studentId) {
        return studentRepository.findById(studentId).orElseThrow(() -> new RuntimeException("not existed this student"));
    }


    @Override
    public void deleteStudent(String id) {

    }

    @Override
    public Student createStudent(StudentCreationRequest request) {
//        Student student = Student.builder()
//                .name(request.getName())
//                .age(request.getAge())
//                .gpa(request.getGpa())
//                .build();

        Student student = studentMapper.toStudent(request);
//        return studentRepository.save(request); // just save entity
        return studentRepository.save(student);
    }

    @Override
    public StudentResponse updateStudent(String id, StudentUpdateRequest request) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("student existed"));
        return studentMapper.toStudentResponse(request, student);
    }
}
