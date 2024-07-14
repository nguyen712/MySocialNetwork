package com.demospring.socialnetwork.util.mapper;

import com.demospring.socialnetwork.dto.request.PostRequest;
import com.demospring.socialnetwork.dto.response.PostResponse;
import com.demospring.socialnetwork.entity.Post;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import javax.lang.model.element.Element;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PostMapper {
    Post toPost(PostRequest postRequest);
    @Mapping(source = "id", target = "id")
    @Mapping(source = "createdBy", target = "createdBy")
    @Mapping(source = "modifiedBy", target = "modifiedBy")
    @Mapping(source = "createdDate", target = "createdDate")
    @Mapping(source = "modifiedDate", target = "modifiedDate")
    PostResponse toPostResponse(Post post);
    List<PostResponse> toPostResponses(List<Post> posts);
    void UpdatePost(@MappingTarget Post post, PostRequest postRequest);
}
