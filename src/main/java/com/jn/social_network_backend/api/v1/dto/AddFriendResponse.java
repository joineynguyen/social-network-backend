package com.jn.social_network_backend.api.v1.dto;

import com.jn.social_network_backend.domain.AddFriendStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddFriendResponse {

    private Long requesterId;
    private String requesterFirstName;
    private String requesterLastName;
    private Long requesteeId;
    private String requesteeFirstName;
    private String requesteeLastName;
    private AddFriendStatus status;
}
