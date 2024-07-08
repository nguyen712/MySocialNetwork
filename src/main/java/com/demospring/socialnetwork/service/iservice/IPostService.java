package com.demospring.socialnetwork.service.iservice;

import com.demospring.socialnetwork.dto.request.PostRequest;
import com.demospring.socialnetwork.dto.response.PostResponse;
import org.springframework.stereotype.Service;

@Service
public interface IPostService {
    PostResponse createPost(PostRequest postRequest) throws Exception;
    Post
}
