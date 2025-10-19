package com.jn.social_network_backend.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Optional;
import java.util.Queue;
import java.util.Set;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jn.social_network_backend.api.v1.dto.AcceptFriendRequest;
import com.jn.social_network_backend.api.v1.dto.AddFriendRequest;
import com.jn.social_network_backend.api.v1.dto.FriendRecommendationRequest;
import com.jn.social_network_backend.domain.FriendshipStatus;
import com.jn.social_network_backend.domain.exception.FriendshipAlreadyExistsException;
import com.jn.social_network_backend.domain.exception.InvalidFriendshipStateException;
import com.jn.social_network_backend.domain.exception.NoActiveFriendshipsFoundException;
import com.jn.social_network_backend.domain.exception.ResourceNotFoundException;
import com.jn.social_network_backend.persistence.entity.Friendship;
import com.jn.social_network_backend.persistence.entity.User;
import com.jn.social_network_backend.persistence.repository.FriendshipRepository;
import com.jn.social_network_backend.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendshipService {

    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;

    public Friendship requestFriend(AddFriendRequest request) {
        User requester = userRepository.findById(request.getRequesterId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        User requestee = userRepository.findById(request.getRequesteeId())
                .orElseThrow(() -> new ResourceNotFoundException("Friend not found"));

        Optional<Friendship> existingFriendship = friendshipRepository.findByUserIdBidirectional(requester, requestee);

        if (existingFriendship.isPresent()) {
            throw new FriendshipAlreadyExistsException(
                    String.format("Friendship already exists between user {} and user {}", request.getRequesterId(),
                            request.getRequesteeId()));
        }

        Friendship friendship = new Friendship();
        friendship.setUserId(requester);
        friendship.setFriendId(requestee);
        friendship.setStatus(FriendshipStatus.PENDING);
        friendship.setUpdatedBy(requester);

        return friendshipRepository.save(friendship);
    }

    public Friendship acceptFriend(AcceptFriendRequest request) {
        User acceptor = userRepository.findById(request.getAcceptorId())
                .orElseThrow(() -> new ResourceNotFoundException("Acceptor user not found."));

        User requester = userRepository.findById(request.getAccepteeId())
                .orElseThrow(() -> new ResourceNotFoundException("Requester user not found."));

        // validate that the friendship exists in the correct direction
        Friendship friendship = friendshipRepository.findByUserIdAndFriendId(requester, acceptor)
                .orElseThrow(
                        () -> new ResourceNotFoundException("No pending friend request found between these users."));

        switch (friendship.getStatus()) {
            case PENDING -> {
                friendship.setStatus(FriendshipStatus.ACCEPTED);
                friendship.setUpdatedBy(acceptor);
                return friendshipRepository.save(friendship);
            }
            case ACCEPTED ->
                throw new InvalidFriendshipStateException("This friend request has already been accepted.");
            case BLOCKED -> {
                if (friendship.getUpdatedBy() == acceptor) {
                    throw new InvalidFriendshipStateException(
                            "You have blocked this person. Unblock before accepting.");
                } else if (friendship.getUpdatedBy() == requester) {
                    throw new InvalidFriendshipStateException(
                            "The requester has blocked you. Cannot accept this request.");
                } else {
                    throw new InvalidFriendshipStateException("Unknown block state for this friendship record.");
                }
            }
            default ->
                throw new InvalidFriendshipStateException("Unexpected friendship status: " + friendship.getStatus());
        }
    }

    public List<Long> recommendFriends(FriendRecommendationRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found."));

        Queue<Long> queue = new LinkedList<>();
        HashSet<Long> visited = new HashSet<>();
        HashMap<Long, Integer> depthMap = new HashMap<>();
        HashSet<Long> recommendedFriends = new HashSet<>();
        int maxDepth = 2;
        
        visited.add(user.getId());
        depthMap.put(user.getId(), 0);
        queue.add(user.getId());

        while (!queue.isEmpty()) {
            Long currUser = queue.poll();
            int currUserDepth = depthMap.get(currUser);

            if (currUserDepth < maxDepth) {
                List<Long> directFriends = friendshipRepository.findDirectFriends(currUser);

                for (Long friend : directFriends) {
                    if (!visited.contains(friend)) {
                        visited.add(friend);
                        depthMap.put(friend, currUserDepth + 1 );
                        queue.add(friend);

                        if (currUserDepth + 1 == maxDepth) {
                            recommendedFriends.add(friend);
                        }
                    }
                }
            }
        }

        return recommendedFriends.stream()
            .sorted()
            .toList();
    }
}
