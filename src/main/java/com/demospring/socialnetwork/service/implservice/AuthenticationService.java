package com.demospring.socialnetwork.service.implservice;

import com.demospring.socialnetwork.dto.request.IntrospectRequest;
import com.demospring.socialnetwork.dto.request.LoginRequest;
import com.demospring.socialnetwork.dto.request.UserRequest;
import com.demospring.socialnetwork.dto.response.AuthenticationResponse;
import com.demospring.socialnetwork.dto.response.IntrospectResponse;
import com.demospring.socialnetwork.entity.User;
import com.demospring.socialnetwork.repository.UserRepository;
import com.demospring.socialnetwork.service.iservice.IAuthenticationService;
import com.demospring.socialnetwork.util.exception.AppException;
import com.demospring.socialnetwork.util.exception.ErrorCode;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Date;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthenticationService implements IAuthenticationService {

    UserRepository userRepository;
    PasswordEncoder passwordEncoder;
    UserService userService;

    @Value("${JWT.PRIVATE_KEY}")
    @NonFinal
    private String PRIVATE_KEY;

    @Value("${DEFAULT_PASSWORD_LOGIN_GOOGLE}")
    @NonFinal
    private String DEFAULT_PASSWORD_LOGIN_GOOGLE;

    @Override
    public AuthenticationResponse authenticate(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername()).orElseThrow(() -> new AppException(ErrorCode.USER_IS_NOT_EXISTED));
        var isAuthenticated = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
        if (!isAuthenticated) throw new AppException(ErrorCode.UNAUTHENTICATE);
        return AuthenticationResponse.builder()
                .authenticated(isAuthenticated)
                .token(generateToken(user))
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public AuthenticationResponse signInGoogle(OAuth2AuthenticationToken authentication) {
        var username = authentication.getPrincipal().getAttributes().get("email");
        boolean userIsExisted = userRepository.findByUsername(username.toString()).isPresent();

        if (!userIsExisted) {
            UserRequest userRequest = mappToUserRequest(authentication);
            try {
                var user = userService.createUser(userRequest);
            } catch (Exception ex) {
                log.error(ex.getMessage());
                throw new AppException(ErrorCode.ADD_UNSUCCESSFULLY);
            }
        }
        return authenticate(LoginRequest.builder()
                .username(username.toString())
                .password(DEFAULT_PASSWORD_LOGIN_GOOGLE)
                .build());
    }

    @Override
    public IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException {
        var token = request.getToken();
        JWSVerifier jwsVerifier = new MACVerifier(PRIVATE_KEY.getBytes());
        SignedJWT jwtSigned = SignedJWT.parse(token);
        var verified = jwtSigned.verify(jwsVerifier);
        return IntrospectResponse.builder().valid(verified).build();
    }

    private UserRequest mappToUserRequest(OAuth2AuthenticationToken authentication) {
        var username = authentication.getPrincipal().getAttributes().get("email").toString();
        var name = authentication.getPrincipal().getAttributes().get("name").toString();
        var family_name = authentication.getPrincipal().getAttributes().get("family_name").toString();
        var firstName = name.split(" " + family_name);
        return UserRequest.builder()
                .email(username)
                .username(username)
                .firstName(Arrays.stream(firstName).reduce((name1, name2) -> name1 + " " + name2).get())
                .lastName(family_name)
                .password(DEFAULT_PASSWORD_LOGIN_GOOGLE)
                .build();
    }

    private String generateToken(User user) {
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(user.getUsername())
                .expirationTime(new Date(Instant.now().plus(1, ChronoUnit.HOURS).plus(30, ChronoUnit.MINUTES).toEpochMilli()))
                .issuer("socialNetwork.com")
                .issueTime(new Date())
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());
        JWSObject jwsObject = new JWSObject(jwsHeader, payload);
        try {
            jwsObject.sign(new MACSigner(PRIVATE_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}
