package com.demospring.socialnetwork.service.iservice;

import com.demospring.socialnetwork.dto.request.ActionWithFriendRequest;
import com.demospring.socialnetwork.dto.request.FriendRequest;
import com.demospring.socialnetwork.dto.response.FriendResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IFriendService {
    FriendResponse makeFriend(FriendRequest friendRequest);
    FriendResponse actionWithFriend(ActionWithFriendRequest friendRequest);

}
