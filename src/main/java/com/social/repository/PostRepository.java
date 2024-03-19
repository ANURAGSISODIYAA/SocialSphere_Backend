package com.social.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.social.models.Post;

public interface PostRepository extends JpaRepository<Post, Integer>{
    
	@Query("Select p from Post p where p.user.id =:userId")
	List<Post> findPostByUserid(Integer userId);
	
}
