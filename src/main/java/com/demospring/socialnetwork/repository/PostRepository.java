package com.demospring.socialnetwork.repository;

import com.demospring.socialnetwork.entity.Post;
import com.demospring.socialnetwork.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository  extends JpaRepository<Post, String> {

}
