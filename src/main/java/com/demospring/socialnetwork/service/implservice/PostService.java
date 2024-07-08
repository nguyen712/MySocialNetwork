package com.demospring.socialnetwork.service.implservice;

import com.demospring.socialnetwork.dto.request.PostRequest;
import com.demospring.socialnetwork.dto.response.PostResponse;
import com.demospring.socialnetwork.repository.PostRepository;
import com.demospring.socialnetwork.repository.UserRepository;
import com.demospring.socialnetwork.service.iservice.IPostService;
import com.demospring.socialnetwork.util.exception.AppException;
import com.demospring.socialnetwork.util.exception.ErrorCode;
import com.demospring.socialnetwork.util.mapper.PostMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PostService implements IPostService {
    PostMapper postMapper;
    UserRepository userRepository;
    PostRepository postRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public PostResponse createPost(PostRequest postRequest) throws Exception {
        var context = SecurityContextHolder.getContext();
        var name = context.getAuthentication().getName();
        var user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_IS_NOT_EXISTED));
        var post = postMapper.toPost(postRequest);
        post.setCreatedBy(user.getId());
        post.setUser(user);
        post.setCreatedDate(new Date());
        try {
            postRepository.save(post);
            return postMapper.toPostResponse(post);
        }catch (Exception e){
            log.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }


}
