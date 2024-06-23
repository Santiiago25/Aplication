package com.swagger.presentation.controller;

import com.swagger.presentation.dto.AuthCreateUserRequest;
import com.swagger.presentation.dto.AuthLoginRequest;
import com.swagger.presentation.dto.AuthResponse;
import com.swagger.service.implementation.UserDetailServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("permitAll()")
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private UserDetailServiceImpl userDetailService;

    @PostMapping("/log-in")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthLoginRequest userRequest) {
        return new ResponseEntity<>(this.userDetailService.loginUser(userRequest), HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> register(@RequestBody @Valid AuthCreateUserRequest authCreateUser){

        ResponseEntity<AuthResponse> responseEntity = new ResponseEntity<>(this.userDetailService.createUser(authCreateUser), HttpStatus.CREATED);
        System.out.println(authCreateUser);
        return responseEntity;

    }


}