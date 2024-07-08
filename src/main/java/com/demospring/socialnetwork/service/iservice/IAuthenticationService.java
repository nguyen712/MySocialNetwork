package com.demospring.socialnetwork.service.iservice;

import com.demospring.socialnetwork.dto.request.IntrospectRequest;
import com.demospring.socialnetwork.dto.request.LoginRequest;
import com.demospring.socialnetwork.dto.response.AuthenticationResponse;
import com.demospring.socialnetwork.dto.response.IntrospectResponse;
import com.nimbusds.jose.JOSEException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public interface IAuthenticationService {
    AuthenticationResponse authenticate(LoginRequest loginRequest);
    AuthenticationResponse signInGoogle(OAuth2AuthenticationToken authentication);
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;
}
