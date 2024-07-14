package com.demospring.socialnetwork.controller;

import com.demospring.socialnetwork.dto.request.IntrospectRequest;
import com.demospring.socialnetwork.dto.request.LoginRequest;
import com.demospring.socialnetwork.dto.response.ApiResponse;
import com.demospring.socialnetwork.dto.response.AuthenticationResponse;
import com.demospring.socialnetwork.dto.response.IntrospectResponse;
import com.demospring.socialnetwork.service.iservice.IAuthenticationService;
import com.demospring.socialnetwork.util.message.Message;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Tag(name = "Auth")
public class AuthenticationController {

    IAuthenticationService authenticationService;

    @GetMapping("/sign-in-google")
    public ApiResponse<AuthenticationResponse> signInGoogle(OAuth2AuthenticationToken authentication) {
        var authenticationRes = authenticationService.signInGoogle(authentication);
        return ApiResponse.<AuthenticationResponse>builder()
                .code(HttpStatus.OK.value())
                .data(AuthenticationResponse.builder()
                        .authenticated(authenticationRes.isAuthenticated())
                        .token(authenticationRes.getToken())
                        .build())
                .message(Message.SuccessMessage.AUTHENTICATE_SUCCESS)
                .build();
    }

    @PostMapping("/login")
    public ApiResponse<AuthenticationResponse> login(@RequestBody LoginRequest loginRequest){
        var authenticationRes = authenticationService.authenticate(loginRequest);
        return ApiResponse.<AuthenticationResponse>builder()
                .code(HttpStatus.OK.value())
                .data(AuthenticationResponse.builder()
                        .authenticated(authenticationRes.isAuthenticated())
                        .token(authenticationRes.getToken())
                        .build())
                .message(Message.SuccessMessage.AUTHENTICATE_SUCCESS)
                .build();
    }

    @PostMapping("/introspect")
    public ApiResponse<IntrospectResponse> Authenticate(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {
        var result = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .code(HttpStatus.OK.value())
                .data(result)
                .message(Message.SuccessMessage.INTROSPECT_SUCCESS)
                .build();
    }
}
