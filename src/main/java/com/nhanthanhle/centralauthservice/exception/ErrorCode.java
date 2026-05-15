package com.nhanthanhle.centralauthservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
@Getter
public enum ErrorCode {
    INVALID_KEY(9999,"INVALID KEY", HttpStatus.BAD_REQUEST),
    USER_EXISTED(1002, "User Existed", HttpStatus.NOT_FOUND),
    UNCASETORIZE_EXCEPTION(1003, "UNCASETORIZE EXCEPTION", HttpStatus.INTERNAL_SERVER_ERROR),
    USERNAME_INVALID(1004, "Username must be at least 3 character", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1005, "Password must be at least 8 character", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1006, "User NOT Existed", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1007, "Cannot login", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1008, "U not have permission", HttpStatus.FORBIDDEN),
    INVALID_DOB(1009, "YOU ARE NOT AGE ENOUGH", HttpStatus.BAD_REQUEST)


    ;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

    private int code;
    private String message;
    private HttpStatusCode httpStatusCode;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
