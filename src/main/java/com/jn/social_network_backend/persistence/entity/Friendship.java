package com.jn.social_network_backend.persistence.entity;

import com.jn.social_network_backend.domain.FriendshipStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "friendships", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"userId", "friendId"})
})
@Data
@RequiredArgsConstructor
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // The user who initiated the friendship
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User userId;

    // The user who is being added as a friend
    @ManyToOne
    @JoinColumn(name = "friend_id", nullable = false)
    private User friendId;

    @Enumerated(EnumType.STRING)
    private FriendshipStatus status;
}
