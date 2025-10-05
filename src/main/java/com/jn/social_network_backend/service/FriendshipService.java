package com.jn.social_network_backend.service;


import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jn.social_network_backend.api.v1.dto.AcceptFriendRequest;
import com.jn.social_network_backend.api.v1.dto.AddFriendRequest;
import com.jn.social_network_backend.domain.FriendshipStatus;
import com.jn.social_network_backend.domain.exception.FriendshipAlreadyExistsException;
import com.jn.social_network_backend.domain.exception.InvalidFriendshipStateException;
import com.jn.social_network_backend.domain.exception.ResourceNotFoundException;
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
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        User requestee = userRepository.findById(request.getRequesteeId())
            .orElseThrow(() -> new ResourceNotFoundException("Friend not found"));

        Optional<Friendship> existingFriendship = friendshipRepository.findByUserIdBidirectional(requester, requestee);
        
        if (existingFriendship.isPresent()) {
            throw new FriendshipAlreadyExistsException(
                String.format("Friendship already exists between user {} and user {}", request.getRequesterId(), request.getRequesteeId()
                )
            );
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
}
