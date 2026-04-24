package com.nhanthanhle.centralauthservice.repository;

import com.nhanthanhle.centralauthservice.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, String> {
}
