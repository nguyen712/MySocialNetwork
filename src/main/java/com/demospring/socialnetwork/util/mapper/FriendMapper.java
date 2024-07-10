package com.demospring.socialnetwork.util.mapper;

import com.demospring.socialnetwork.dto.request.FriendRequest;
import com.demospring.socialnetwork.dto.response.FriendResponse;
import com.demospring.socialnetwork.entity.Friend;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FriendMapper {
    Friend toFriend(FriendRequest friendRequest);
    FriendResponse toFriendResponse(Friend friend);
}
