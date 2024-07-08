package com.demospring.socialnetwork.controller;

import com.demospring.socialnetwork.dto.request.PostRequest;
import com.demospring.socialnetwork.dto.response.ApiResponse;
import com.demospring.socialnetwork.dto.response.PostResponse;
import com.demospring.socialnetwork.service.implservice.PostService;
import com.demospring.socialnetwork.service.iservice.IPostService;
import com.demospring.socialnetwork.util.exception.AppException;
import com.demospring.socialnetwork.util.exception.ErrorCode;
import com.demospring.socialnetwork.util.message.Message;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PostController {
    IPostService postService;

    @PostMapping
    public ApiResponse<PostResponse> createPost(@RequestBody PostRequest postRequest) {
        try{
            var post = postService.createPost(postRequest);
            return ApiResponse.<PostResponse>builder()
                    .data(post)
                    .message(Message.SuccessMessage.ADD_SUCCESSFULLY)
                    .build();
        }catch (Exception ex){
            throw new AppException(ErrorCode.ADD_UNSUCCESSFULLY);
        }
    }
}
