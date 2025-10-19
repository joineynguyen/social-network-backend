package com.jn.social_network_backend.api.v1.dto;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FriendRecommendationResponse {
    Long userId;
    List<Long> recommendedFriends;
}
