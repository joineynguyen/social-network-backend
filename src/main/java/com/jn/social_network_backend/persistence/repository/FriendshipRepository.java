package com.jn.social_network_backend.persistence.repository;

import java.util.ArrayList;
import java.util.List;
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

        @Query(value = """
                        SELECT f
                        FROM Friendship f
                        WHERE (f.userId = :userId AND f.friendId = :friendId)
                        OR (f.userId = :friendId AND f.friendId = :userId)
                        """, nativeQuery = false)
        Optional<Friendship> findByUserIdBidirectional(
                        @Param("userId") User userId,
                        @Param("friendId") User friendId);

        @Query(value = """
                        SELECT CASE
                                WHEN f.userId.id = :userId THEN f.friendId.id
                                ELSE f.userId.id
                                END
                        FROM Friendship f
                        WHERE f.status = 'ACCEPTED'
                        AND (:userId = f.userId.id OR :userId = f.friendId.id)
                        """, nativeQuery = false)
        List<Long> findDirectFriends(
                        @Param("userId") Long user);

}
