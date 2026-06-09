package com.nhanthanhle.centralauthservice.dto.request;

import com.nhanthanhle.centralauthservice.validator.DobConstraint;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
public class UserCreationRequest {
    @Size(min = 3, message = "USERNAME_INVALID")
    private String username;
    @Size(min = 8, message = "PASSWORD_INVALID")
    private String password;
    private String lastname;
    private String firstname;
    @DobConstraint(min = 18, message = "INVALID_DOB")
    private LocalDate dob;


}
