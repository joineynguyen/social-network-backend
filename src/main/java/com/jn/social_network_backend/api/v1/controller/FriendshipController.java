package com.jn.social_network_backend.api.v1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jn.social_network_backend.api.v1.dto.AcceptFriendRequest;
import com.jn.social_network_backend.api.v1.dto.AcceptFriendResponse;
import com.jn.social_network_backend.api.v1.dto.AddFriendRequest;
import com.jn.social_network_backend.api.v1.dto.AddFriendResponse;
import com.jn.social_network_backend.api.v1.mapper.FriendshipMapper;
import com.jn.social_network_backend.persistence.entity.Friendship;
import com.jn.social_network_backend.service.FriendshipService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/friendships")
@RequiredArgsConstructor
public class FriendshipController {

    private final FriendshipService friendshipService;
    private final FriendshipMapper friendshipMapper;

    @PostMapping("/request")
    public ResponseEntity<AddFriendResponse> requestFriend(@Valid @RequestBody AddFriendRequest request) {
        Friendship friendshipCreated = friendshipService.requestFriend(request);
        AddFriendResponse response = friendshipMapper.toAddFriendResponseDto(friendshipCreated);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @PostMapping("/accept")
    public ResponseEntity<AcceptFriendResponse> acceptFriend(@Valid @RequestBody AcceptFriendRequest request) {
        Friendship friendshipAccepted = friendshipService.acceptFriend(request);
        AcceptFriendResponse response = friendshipMapper.toAcceptFriendResponseDto(friendshipAccepted);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(response);

    }
}
