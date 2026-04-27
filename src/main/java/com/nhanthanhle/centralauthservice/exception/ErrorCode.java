package com.nhanthanhle.centralauthservice.exception;

public enum ErrorCode {
    INVALID_KEY(9999,"INVALID KEY"),
    USER_EXISTED(1002, "User Existed"),
    UNCASETORIZE_EXCEPTION(1003, "UNCASETORIZE EXCEPTION"),
    USERNAME_INVALID(1004, "Username must be at least 3 character"),
    PASSWORD_INVALID(1005, "Password must be at least 8 character"),
    USER_NOT_EXISTED(1006, "User NOT Existed"),

    ;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
