package com.jn.social_network_backend.api.v1.dto;

import com.jn.social_network_backend.domain.AddFriendStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AcceptFriendResponse {

    private Long acceptorId;
    private String acceptorFirstName;
    private String acceptorLastName;
    private Long requesterId;
    private String requesterFirstName;
    private String requesterLastName;
    private AddFriendStatus status;
}
