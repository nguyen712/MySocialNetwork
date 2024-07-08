package com.demospring.socialnetwork.service.implservice;

import com.demospring.socialnetwork.dto.request.UserRequest;
import com.demospring.socialnetwork.dto.response.UserResponse;
import com.demospring.socialnetwork.entity.User;
import com.demospring.socialnetwork.repository.UserRepository;
import com.demospring.socialnetwork.service.iservice.IGeoLocationService;
import com.demospring.socialnetwork.service.iservice.IUserService;
import com.demospring.socialnetwork.util.exception.AppException;
import com.demospring.socialnetwork.util.exception.ErrorCode;
import com.demospring.socialnetwork.util.mapper.UserMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService implements IUserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;
    IGeoLocationService geoLocationService;

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
    public User setLocationOfUser(String clientIp, String userId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_IS_NOT_EXISTED));
        try{
           var location = geoLocationService.getLocationById(clientIp);
            user.setLatitude(location.getLatitude());
            user.setLongitude(location.getLongitude());
            userRepository.save(user);
            return user;
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
}
