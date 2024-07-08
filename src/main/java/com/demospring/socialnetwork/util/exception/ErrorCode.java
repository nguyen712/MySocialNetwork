package com.demospring.socialnetwork.util.exception;

import org.springframework.http.HttpStatus;

import java.net.http.HttpRequest;

public enum ErrorCode {
    USER_IS_NOT_EXISTED(HttpStatus.NOT_FOUND.value(), "User is not existed"),
    SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "System error"),
    UNAUTHENTICATE(HttpStatus.FORBIDDEN.value(), "Username or Password is incorrect"),
    ADD_UNSUCCESSFULLY(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Create user unsuccessfully.")
    ;
    private int errorCode;
    private String message;
    ErrorCode(int errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public int getErrorCode() {
        return errorCode;
    }
    public String getMessage() {
        return message;
    }
}
