package com.demospring.socialnetwork.service.implservice;

import com.demospring.socialnetwork.dto.request.ActionWithFriendRequest;
import com.demospring.socialnetwork.dto.request.FriendRequest;
import com.demospring.socialnetwork.dto.response.FriendResponse;
import com.demospring.socialnetwork.dto.response.UserResponse;
import com.demospring.socialnetwork.entity.Friend;
import com.demospring.socialnetwork.repository.FriendRepository;
import com.demospring.socialnetwork.repository.UserRepository;
import com.demospring.socialnetwork.service.iservice.IFriendService;
import com.demospring.socialnetwork.util.enums.RelationShipStatus;
import com.demospring.socialnetwork.util.exception.AppException;
import com.demospring.socialnetwork.util.exception.ErrorCode;
import com.demospring.socialnetwork.util.mapper.FriendMapper;
import com.demospring.socialnetwork.util.mapper.UserMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class FriendService implements IFriendService {

    FriendRepository friendRepository;
    UserRepository userRepository;
    FriendMapper friendMapper;
    UserMapper userMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public FriendResponse makeFriend(FriendRequest friendRequest) {
        var context = SecurityContextHolder.getContext();
        var username = context.getAuthentication().getName();
        var user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_IS_NOT_EXISTED));
        var friendId = userRepository.findById(friendRequest.getUserId()).orElseThrow(() ->new AppException(ErrorCode.USER_IS_NOT_EXISTED));
        if(user.getId().equals(friendId.getId())) throw new AppException(ErrorCode.CAN_NOT_ADD_FRIEND_TO_YOURSELF);

        var isUserAndFriendIdIsExisted = friendRepository.existsByUserAndFriend(user, friendId);
        if(isUserAndFriendIdIsExisted) throw new AppException(ErrorCode.FRIEND_IS_EXISTED);
        Friend friend = new Friend();
        friend.setStatus(RelationShipStatus.PENDING);
        friend.setUser(user);
        friend.setFriend(friendId);
        friend.setCreatedDate(new Date());
        friendRepository.save(friend);
        return friendMapper.toFriendResponse(friend);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public FriendResponse actionWithFriend(ActionWithFriendRequest friendRequest) {
        var friend = friendRepository.findById(friendRequest.getFriendId()).orElseThrow(() -> new AppException(ErrorCode.FRIEND_RELATIONSHIP_IS_NOT_EXISTED));
        friend.setStatus(friendRequest.getAction());
        friend.setModifiedDate(new Date());
        friendRepository.save(friend);
        return friendMapper.toFriendResponse(friend);
    }


}
