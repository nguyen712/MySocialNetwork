package com.demospring.socialnetwork.repository;

import com.demospring.socialnetwork.entity.Post;
import com.demospring.socialnetwork.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository  extends JpaRepository<Post, String> {
    @Query("select p from Post p where p.user.id in :userId")
    List<Post> findAllByUserId(List<String> userId);
}
