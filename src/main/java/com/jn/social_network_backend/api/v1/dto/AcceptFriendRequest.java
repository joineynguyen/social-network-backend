package com.jn.social_network_backend.api.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AcceptFriendRequest {
    Long acceptorId;
    Long accepteeId;
}
