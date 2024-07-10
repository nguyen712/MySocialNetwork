package com.demospring.socialnetwork.controller;

import com.demospring.socialnetwork.dto.request.ActionWithFriendRequest;
import com.demospring.socialnetwork.dto.request.FriendRequest;
import com.demospring.socialnetwork.dto.response.ApiResponse;
import com.demospring.socialnetwork.dto.response.FriendResponse;
import com.demospring.socialnetwork.service.iservice.IFriendService;
import com.demospring.socialnetwork.util.message.Message;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/friend")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Friend")
public class FriendController {
    IFriendService friendService;

    @PostMapping("/add-friend")
    public ApiResponse<FriendResponse> makeFriend(@RequestBody FriendRequest friendRequest){
        var friendResponse = friendService.makeFriend(friendRequest);
        return ApiResponse.<FriendResponse>builder()
                .data(friendResponse)
                .code(HttpStatus.CREATED.value())
                .message(Message.SuccessMessage.ADD_SUCCESSFULLY)
                .build();
    }
    @PostMapping("/action-with-friend-relationship")
    public ApiResponse<FriendResponse> accointWithInvite(@RequestBody ActionWithFriendRequest friendRequest){
        var friendResponse = friendService.actionWithFriend(friendRequest);
        return ApiResponse.<FriendResponse>builder()
                .data(friendResponse)
                .code(HttpStatus.CREATED.value())
                .message(Message.SuccessMessage.ADD_SUCCESSFULLY)
                .build();
    }
}
