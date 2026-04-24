package com.nhanthanhle.centralauthservice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentCreationRequest {
    String name;
    Integer age;
    Double gpa;
}
