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
    USERNAME_IS_EXISTED(HttpStatus.BAD_REQUEST.value(), "Username is existed."),
    CAN_NOT_GET_LOCATION(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Can not get location in Geo Max Mind."),
    FRIEND_IS_EXISTED(HttpStatus.BAD_REQUEST.value(), "This friend used to be the friend relationship with user."),
    FRIEND_RELATIONSHIP_IS_NOT_EXISTED(HttpStatus.NOT_FOUND.value(), "Friend Relationship is not existed."),
    CAN_NOT_ADD_FRIEND_TO_YOURSELF(HttpStatus.BAD_REQUEST.value(), "Can not add friend to yourself."),
    POST_ID_IS_BLANK(HttpStatus.BAD_REQUEST.value(), "Post id can not be blank."),
    USER_IS_NOT_A_AUTHOR_OF_POST(HttpStatus.BAD_REQUEST.value(), "User is not a author of a post."),
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
