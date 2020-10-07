package com.jwt.controller;

import com.jwt.dto.AuthenticationRequest;
import com.jwt.dto.AuthenticationResponse;
import com.jwt.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        return tokenService.login(authenticationRequest);
    }

    @GetMapping("/test-authentication")
    public String test() throws Exception {
        return "test-authentication";
    }
}
