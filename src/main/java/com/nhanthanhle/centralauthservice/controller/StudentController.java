package com.nhanthanhle.centralauthservice.controller;

import com.nhanthanhle.centralauthservice.dto.request.NhanResponse;
import com.nhanthanhle.centralauthservice.dto.request.StudentCreationRequest;
import com.nhanthanhle.centralauthservice.dto.request.StudentUpdateRequest;
import com.nhanthanhle.centralauthservice.dto.response.StudentResponse;
import com.nhanthanhle.centralauthservice.entity.Student;
import com.nhanthanhle.centralauthservice.service.impl.StudentServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
@Tag(name = "API student", description = "nhan handmade")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class StudentController {

    StudentServiceImpl studentService;

    @GetMapping()
    public ResponseEntity<List<Student>> getAll() {
        return ResponseEntity.status(HttpStatus.OK).body(studentService.getAll());
    }

    @PostMapping()
    public NhanResponse<Student> createStudent(@RequestBody StudentCreationRequest request) {

        NhanResponse<Student> nhanResponse = new NhanResponse();
        nhanResponse.setCode(2000);
        nhanResponse.setResult(studentService.createStudent(request));

        return nhanResponse;
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> getStudent(@PathVariable("studentId") String studentId) {


        return ResponseEntity.status(HttpStatus.OK).body(studentService.getStudent(studentId));

    }

    @PutMapping("/{studentId}")
    public StudentResponse updateStudent(@PathVariable("studentId") String studentId, @RequestBody StudentUpdateRequest request) {

        return studentService.updateStudent(studentId, request);

    }

}
