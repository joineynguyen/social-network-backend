package com.jn.social_network_backend.api.v1.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jn.social_network_backend.api.v1.dto.AddFriendRequest;
import com.jn.social_network_backend.api.v1.dto.AddFriendResponse;
import com.jn.social_network_backend.domain.AddFriendStatus;
import com.jn.social_network_backend.persistence.entity.Friendship;
import com.jn.social_network_backend.persistence.entity.User;
import com.jn.social_network_backend.service.FriendshipService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/friendships")
@RequiredArgsConstructor
public class FriendshipController {

    private final FriendshipService friendshipService;

    @PostMapping("/request")
    public ResponseEntity<AddFriendResponse> requestFriend(@Valid @RequestBody AddFriendRequest request) {
        Friendship friendshipCreated = friendshipService.requestFriend(request);

        User requester = friendshipCreated.getUserId();
        User requestee = friendshipCreated.getFriendId();

        AddFriendResponse addFriendResponse = AddFriendResponse.builder()
            .requesterId(requester.getId())
            .requesterFirstName(requester.getFirstName())
            .requesterLastName(requester.getLastName())
            .requesteeId(requestee.getId())
            .requesteeFirstName(requestee.getFirstName())
            .requesteeLastName(requestee.getLastName())
            .status(AddFriendStatus.SUCCESS)
            .build();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(addFriendResponse);
    }
}
