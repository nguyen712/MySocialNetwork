package com.demospring.socialnetwork.util.mapper;

import com.demospring.socialnetwork.dto.request.PostRequest;
import com.demospring.socialnetwork.dto.response.PostResponse;
import com.demospring.socialnetwork.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post toPost(PostRequest postRequest);
    PostResponse toPostResponse(Post post);
}
