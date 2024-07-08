package com.demospring.socialnetwork.util.mapper;

import com.demospring.socialnetwork.dto.request.UserRequest;
import com.demospring.socialnetwork.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserRequest user);
}
