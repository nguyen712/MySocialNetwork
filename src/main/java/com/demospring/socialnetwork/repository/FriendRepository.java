package com.demospring.socialnetwork.repository;

import com.demospring.socialnetwork.entity.Friend;
import com.demospring.socialnetwork.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend, String> {
    boolean existsByUserAndFriend(User user, User friend);

    @Query("select f from Friend f where (f.user.id = :userId or f.friend.id = :userId) and f.status = 1")
    List<Friend> getAllFriendsByUserId(String userId);
}
