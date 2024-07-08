package com.demospring.socialnetwork.repository;

import com.demospring.socialnetwork.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);

    @Query(value = "select u from User u join Friend f on u.id = f.user.id " +
            "where f.user.id != :userId")
    List<User> findAllUsersAvailableForAddFriend(@Param("userId") String userId);
}
