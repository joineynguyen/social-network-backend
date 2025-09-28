package com.jn.social_network_backend.service;

import org.springframework.stereotype.Service;

import com.jn.social_network_backend.api.v1.dto.AddFriendRequest;
import com.jn.social_network_backend.domain.FriendshipStatus;
import com.jn.social_network_backend.persistence.entity.Friendship;
import com.jn.social_network_backend.persistence.entity.User;
import com.jn.social_network_backend.persistence.repository.FriendshipRepository;
import com.jn.social_network_backend.persistence.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FriendshipService {

    private final UserRepository userRepository;
    private final FriendshipRepository friendshipRepository;

    public Friendship requestFriend(AddFriendRequest request) {
        User requester = userRepository.findById(request.getRequesterId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        User requestee = userRepository.findById(request.getRequesteeId())
                .orElseThrow(() -> new EntityNotFoundException("Friend not found"));

        Friendship friendship = new Friendship();
        friendship.setUserId(requester);
        friendship.setFriendId(requestee);
        friendship.setStatus(FriendshipStatus.PENDING);

        return friendshipRepository.save(friendship);
    }
}
