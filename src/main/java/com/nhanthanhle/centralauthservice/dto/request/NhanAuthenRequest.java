package com.nhanthanhle.centralauthservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NhanAuthenRequest {
    private String username;
    private String password;
}
