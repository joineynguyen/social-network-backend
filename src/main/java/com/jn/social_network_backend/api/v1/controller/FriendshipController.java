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
import com.jn.social_network_backend.service.FriendshipService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/friendships")
@RequiredArgsConstructor
public class FriendshipController {

    private final FriendshipService friendshipService;

    @PostMapping("/request")
    public ResponseEntity<AddFriendResponse> requestFriend(@RequestBody AddFriendRequest request) {
        Friendship friendshipCreated = friendshipService.requestFriend(request);

        AddFriendResponse addFriendResponse = new AddFriendResponse();
        addFriendResponse.setRequesterId(friendshipCreated.getUserId().getId());
        addFriendResponse.setRequesterFirstName(friendshipCreated.getUserId().getFirstName());
        addFriendResponse.setRequesterLastName(friendshipCreated.getUserId().getLastName());
        addFriendResponse.setRequesteeId(friendshipCreated.getFriendId().getId());
        addFriendResponse.setRequesteeFirstName(friendshipCreated.getFriendId().getFirstName());
        addFriendResponse.setRequesteeLastName(friendshipCreated.getFriendId().getLastName());
        addFriendResponse.setStatus(AddFriendStatus.SUCCESS);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(addFriendResponse);
    }
}
