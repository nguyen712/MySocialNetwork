package com.demospring.socialnetwork.repository;

import com.demospring.socialnetwork.entity.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActionRepository extends JpaRepository<Action, String> {
    @Query("select a from Action a where a.user.username = :username and a.action = :action")
    Optional<Action> findByUsername(@Param("username") String username, @Param("action") String action);
}
