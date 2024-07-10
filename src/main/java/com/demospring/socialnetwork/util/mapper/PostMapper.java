package com.demospring.socialnetwork.util.mapper;

import com.demospring.socialnetwork.dto.request.PostRequest;
import com.demospring.socialnetwork.dto.response.PostResponse;
import com.demospring.socialnetwork.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post toPost(PostRequest postRequest);
    PostResponse toPostResponse(Post post);
    List<PostResponse> toPostResponses(List<Post> posts);
    void UpdatePost(@MappingTarget Post post, PostRequest postRequest);
}
