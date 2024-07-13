package com.demospring.socialnetwork.service.implservice;

import com.demospring.socialnetwork.dto.request.UserRequest;
import com.demospring.socialnetwork.dto.response.UserAfterUpdateLocationResponse;
import com.demospring.socialnetwork.dto.response.UserResponse;
import com.demospring.socialnetwork.entity.User;
import com.demospring.socialnetwork.repository.FriendRepository;
import com.demospring.socialnetwork.repository.UserRepository;
import com.demospring.socialnetwork.service.iservice.IGeoLocationService;
import com.demospring.socialnetwork.service.iservice.IUserService;
import com.demospring.socialnetwork.util.enums.RelationShipStatus;
import com.demospring.socialnetwork.util.exception.AppException;
import com.demospring.socialnetwork.util.exception.ErrorCode;
import com.demospring.socialnetwork.util.helper.MathHelper;
import com.demospring.socialnetwork.util.mapper.UserMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ResourceLoader;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService implements IUserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    IGeoLocationService geoLocationService;
    FriendRepository friendRepository;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public User createUser(UserRequest userRequest) throws Exception{
        var userNameIsExisted = userRepository.findByUsername(userRequest.getUsername());
        if(userNameIsExisted.isPresent()) throw new AppException(ErrorCode.USERNAME_IS_EXISTED);
        var user = userMapper.toUser(userRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try{
            userRepository.save(user);
            return user;
        }catch (Exception e){
            log.error(e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public UserAfterUpdateLocationResponse setLocationOfUser(String clientIp) {
        var context = SecurityContextHolder.getContext();
        var username = context.getAuthentication().getName();
        var user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_IS_NOT_EXISTED));
        try{
           var location = geoLocationService.getLocationById(clientIp);
            user.setLatitude(location.getLatitude());
            user.setLongitude(location.getLongitude());
            userRepository.save(user);
            return userMapper.toUserMapperUpdateLocationResponse(user);
        }catch (Exception ex){
            throw new AppException(ErrorCode.CAN_NOT_GET_LOCATION);
        }
    }

    @Override
    public List<UserResponse> getAllUserIsAvailable() {
        var context = SecurityContextHolder.getContext();
        var username = context.getAuthentication().getName();
        var allUser = userRepository.findAll();
        var user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_IS_NOT_EXISTED));
        List<User> userList = userRepository.findAllUsersAvailableForAddFriend(user.getId());
        if(!userList.isEmpty()){
            var userAvailable = allUser.removeAll(userList);
        }
        return userMapper.toUserResponseList(allUser);
    }

    @Override
    public List<UserResponse> getAllUserNearly(double requiredDistance) {
        var context = SecurityContextHolder.getContext();
        var username = context.getAuthentication().getName();

        var user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_IS_NOT_EXISTED));
        var allUser = userRepository.findUserHasLatitudeAndLongitude(user.getId());
        if(allUser.isEmpty()) return new ArrayList<UserResponse>();
        List<User> nearbyUsers = allUser.stream()
                .filter(x -> {
                    double distanceOfUser = MathHelper.calculateDistance(user.getLatitude(), user.getLongitude(),
                            x.getLatitude(), x.getLongitude());
                    System.out.println("Distance between user and x: " + distanceOfUser);
                    return distanceOfUser <= requiredDistance;
                })
                .collect(Collectors.toList());
        return userMapper.toUserResponseList(nearbyUsers);
    }

    @Override
    public List<UserResponse> getAllFriends() {
        var context = SecurityContextHolder.getContext();
        var username = context.getAuthentication().getName();
        var userIsExistedOrNot = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_IS_NOT_EXISTED));
        var user = friendRepository.getAllFriendsByUserId(userIsExistedOrNot.getId());
        if(user.isEmpty()){
            log.info("User doesnt have any friends.");
            return new ArrayList<>();
        }
        List<User> friends = user.stream()
                .map(friendship -> {
                    if (friendship.getUser().getId().equals(userIsExistedOrNot.getId())) {
                        return friendship.getFriend();
                    } else {
                        return friendship.getUser();
                    }
                })
                .collect(Collectors.toList());
        return userMapper.toUserResponseList(friends);
    }

}
