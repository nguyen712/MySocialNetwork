package com.demospring.socialnetwork.util.exception;

import org.springframework.http.HttpStatus;

import java.net.http.HttpRequest;

public enum ErrorCode {
    USER_IS_NOT_EXISTED(HttpStatus.NOT_FOUND.value(), "User is not existed"),
    SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR.value(), "System error"),
    UNAUTHENTICATE(HttpStatus.FORBIDDEN.value(), "Username or Password is incorrect"),
    ADD_UNSUCCESSFULLY(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Create user unsuccessfully."),
    POST_IS_NOT_EXISTED(HttpStatus.NOT_FOUND.value(), "Post is not existed"),
    DUPLICATE_ACTION(HttpStatus.CONFLICT.value(), "Post action must be like or dislike. You need to approve like or dislike."),
    INVALID_KEY(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Invalid Key Enum"),
    USERNAME_IS_BLANK(HttpStatus.BAD_REQUEST.value(), "Username can not be blank."),
    PASSWORD_IS_BLANK(HttpStatus.BAD_REQUEST.value(), "Username can not be blank."),
    FIRSTNAME_IS_BLANK(HttpStatus.BAD_REQUEST.value(), "Username can not be blank."),
    LASTNAME_IS_BLANK(HttpStatus.BAD_REQUEST.value(), "Username can not be blank."),

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
