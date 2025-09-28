package com.jn.social_network_backend.api.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AddFriendRequest {

    private Long requesterId;
    private Long requesteeId;
}
