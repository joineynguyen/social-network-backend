package com.jn.social_network_backend.api.v1.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddFriendRequest {

    private Long requesterId;
    private Long requesteeId;
}
