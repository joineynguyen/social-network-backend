package com.jn.social_network_backend.persistence.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.jn.social_network_backend.persistence.entity.Friendship;
import com.jn.social_network_backend.persistence.entity.User;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

        Optional<Friendship> findByUserIdAndFriendId(User userId, User friendId);

        @Query("""
                SELECT f
                FROM Friendship f
                WHERE (f.userId = :userId AND f.friendId = :friendId)
                OR (f.userId = :friendId AND f.friendId = :userId)
                """)
        Optional<Friendship> findByUserIdBidirectional(
                @Param("userId") User userId,
                @Param("friendId") User friendId
        );
}
