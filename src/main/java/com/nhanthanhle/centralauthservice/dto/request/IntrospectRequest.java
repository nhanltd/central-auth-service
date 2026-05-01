package com.nhanthanhle.centralauthservice.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
// introspect: use to verify token
public class IntrospectRequest {
   String token;
}
