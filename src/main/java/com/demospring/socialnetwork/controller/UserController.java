package com.demospring.socialnetwork.controller;

import com.demospring.socialnetwork.dto.request.GeoRequestData;
import com.demospring.socialnetwork.dto.request.UserRequest;
import com.demospring.socialnetwork.dto.response.ApiResponse;
import com.demospring.socialnetwork.dto.response.UserAfterUpdateLocationResponse;
import com.demospring.socialnetwork.dto.response.UserResponse;
import com.demospring.socialnetwork.entity.User;
import com.demospring.socialnetwork.service.iservice.IUserService;
import com.demospring.socialnetwork.util.mapper.UserMapper;
import com.demospring.socialnetwork.util.message.Message;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "User")
public class UserController {
    IUserService userService;
    UserMapper userMapper;

    @PostMapping("/register")
    public ApiResponse<UserResponse> registerUser(@RequestBody UserRequest userRequest, HttpServletRequest request) throws Exception {
        var clientIp = request.getRemoteAddr();
        var user = userService.createUser(userRequest);
        return ApiResponse.<UserResponse>builder()
                .code(HttpStatus.CREATED.value())
                .message(Message.SuccessMessage.ADD_SUCCESSFULLY)
                .data(userMapper.toUserResponse(user))
                .build();
    }

    @GetMapping("/user-avalable")
    public ApiResponse<List<UserResponse>> getUserForAddFriend(){
        List<UserResponse> userResponseList = userService.getAllUserIsAvailable();
        return ApiResponse.<List<UserResponse>>builder()
                .code(HttpStatus.OK.value())
                .data(userResponseList)
                .build();
    }

    @PostMapping("/update-user-ip")
    public ApiResponse<UserAfterUpdateLocationResponse> updateUserLocation(@RequestBody GeoRequestData geoRequestData){
        return ApiResponse.<UserAfterUpdateLocationResponse>builder()
                .code(HttpStatus.OK.value())
                .data(userService.setLocationOfUser(geoRequestData.getClientIp()))
                .build();
    }

    @GetMapping("/user-nearly/{requiredDistance}")
    public ApiResponse<List<UserResponse>> getUserForAddFriend(@PathVariable double requiredDistance){
        List<UserResponse> userResponseList = userService.getAllUserNearly(requiredDistance);
        return ApiResponse.<List<UserResponse>>builder()
                .code(HttpStatus.OK.value())
                .data(userResponseList)
                .build();
    }

    @GetMapping("/user-friends")
    public ApiResponse<List<UserResponse>> getAllFriendOfUser(){
        List<UserResponse> userResponseList = userService.getAllFriends();
        return ApiResponse.<List<UserResponse>>builder()
                .code(HttpStatus.OK.value())
                .data(userResponseList)
                .build();
    }
}
