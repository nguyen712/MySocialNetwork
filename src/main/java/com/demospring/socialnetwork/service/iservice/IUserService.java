package com.demospring.socialnetwork.service.iservice;

import com.demospring.socialnetwork.dto.request.UserRequest;
import com.demospring.socialnetwork.dto.response.UserResponse;
import com.demospring.socialnetwork.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IUserService {
    User createUser(UserRequest userRequest) throws Exception;
    User setLocationOfUser(String clientIp, String userId);
    List<UserResponse> getAllUserIsAvailable();
    List<UserResponse> getAllUserNearly(double requiredDistance);
    List<UserResponse> getAllFriends();
}
