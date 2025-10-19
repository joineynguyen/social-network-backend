package com.jn.social_network_backend;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.jn.social_network_backend.api.v1.dto.FriendRecommendationRequest;
import com.jn.social_network_backend.persistence.entity.User;
import com.jn.social_network_backend.persistence.repository.FriendshipRepository;
import com.jn.social_network_backend.persistence.repository.UserRepository;
import com.jn.social_network_backend.service.FriendshipService;

public class FriendshipServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private FriendshipRepository friendshipRepository;

    @InjectMocks
    private FriendshipService friendshipService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testRecommendFriends_TwoLevelDepth() {
        // Arrange (mock setup)
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setFirstName("John");
        user.setLastName("Nguyen");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // mock friend connections

        when(friendshipRepository.findDirectFriends(1L)).thenReturn(new ArrayList<>(List.of(2L, 3L)));

        when(friendshipRepository.findDirectFriends(2L)).thenReturn(new ArrayList<>(List.of(3L, 4L)));

        when(friendshipRepository.findDirectFriends(3L)).thenReturn(new ArrayList<>(List.of(5L)));
        when(friendshipRepository.findDirectFriends(4L)).thenReturn(new ArrayList<>(List.of(6L)));

        FriendRecommendationRequest request = FriendRecommendationRequest.builder()
            .userId(1L)
            .build();

        // act
        List<Long> recommendations = friendshipService.recommendFriends(request);
        
        System.out.println(recommendations);

        // verify
        //assertTrue(recommendations.containsAll(List.of(4L)));
        





        
    }
}
