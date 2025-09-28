package com.jn.social_network_backend.api.v1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jn.social_network_backend.api.v1.dto.UserRegistrationRequest;
import com.jn.social_network_backend.api.v1.dto.UserRegistrationResponse;
import com.jn.social_network_backend.domain.RegistrationStatus;
import com.jn.social_network_backend.persistence.entity.User;
import com.jn.social_network_backend.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserRegistrationResponse> registerUser(
        @Valid @RequestBody UserRegistrationRequest req) {
            
        User createdUser = userService.registerUser(req);
        
        UserRegistrationResponse resp = new UserRegistrationResponse(
            createdUser.getFirstName(),
            createdUser.getLastName(),
            RegistrationStatus.SUCCESS
        );
        
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(resp);
    }
}
