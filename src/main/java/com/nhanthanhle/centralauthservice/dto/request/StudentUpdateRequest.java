package com.nhanthanhle.centralauthservice.dto.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
public class StudentUpdateRequest {
    String name;
    Integer age;
    Double gpa;
}
