package com.demospring.socialnetwork.controller;

import com.demospring.socialnetwork.dto.request.UserRequest;
import com.demospring.socialnetwork.dto.response.ApiResponse;
import com.demospring.socialnetwork.dto.response.UserResponse;
import com.demospring.socialnetwork.service.iservice.IUserService;
import com.demospring.socialnetwork.util.mapper.UserMapper;
import com.demospring.socialnetwork.util.message.Message;
import io.swagger.v3.oas.annotations.tags.Tag;
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
    public ApiResponse<UserResponse> registerUser(@RequestBody UserRequest userRequest) throws Exception {
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
                .data(userResponseList)
                .build();
    }

}
