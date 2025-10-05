package com.jn.social_network_backend.api.v1.mapper;

import org.springframework.stereotype.Component;

import com.jn.social_network_backend.api.v1.dto.AcceptFriendResponse;
import com.jn.social_network_backend.api.v1.dto.AddFriendResponse;
import com.jn.social_network_backend.domain.AddFriendStatus;
import com.jn.social_network_backend.persistence.entity.Friendship;

@Component
public class FriendshipMapper {
    public AddFriendResponse toAddFriendResponseDto(Friendship friendship) {
        return AddFriendResponse.builder()
                .requesterId(friendship.getUserId().getId())
                .requesterFirstName(friendship.getUserId().getFirstName())
                .requesterLastName(friendship.getUserId().getLastName())
                .requesteeId(friendship.getFriendId().getId())
                .requesteeFirstName(friendship.getFriendId().getFirstName())
                .requesteeLastName(friendship.getFriendId().getLastName())
                .status(AddFriendStatus.SUCCESS)
                .build();
    }

    public AcceptFriendResponse toAcceptFriendResponseDto(Friendship friendship) {
        return AcceptFriendResponse.builder()
                .acceptorId(friendship.getFriendId().getId())
                .acceptorFirstName(friendship.getFriendId().getFirstName())
                .acceptorLastName(friendship.getFriendId().getLastName())
                .requesterId(friendship.getUserId().getId())
                .requesterFirstName(friendship.getUserId().getFirstName())
                .requesterLastName(friendship.getUserId().getLastName())
                .status(AddFriendStatus.SUCCESS)
                .build();
    }
}
