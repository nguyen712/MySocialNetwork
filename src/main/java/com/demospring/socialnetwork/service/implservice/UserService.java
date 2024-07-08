package com.demospring.socialnetwork.service.implservice;

import com.demospring.socialnetwork.dto.request.UserRequest;
import com.demospring.socialnetwork.entity.User;
import com.demospring.socialnetwork.repository.UserRepository;
import com.demospring.socialnetwork.service.iservice.IUserService;
import com.demospring.socialnetwork.util.exception.AppException;
import com.demospring.socialnetwork.util.exception.ErrorCode;
import com.demospring.socialnetwork.util.mapper.UserMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService implements IUserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public User createUser(UserRequest userRequest) throws Exception{
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
}
