package com.social.service;

import java.util.List;

import com.social.models.Post;

public interface PostService {
     
	public Post createPost(Post post,Integer userId) throws Exception;
	
	public String deletePost(Integer postId,Integer userId) throws Exception;
	
	public List<Post> findPostByUserId(Integer userId) throws Exception;
	
	public Post findPostById(Integer postId) throws Exception;
	
	public List<Post> findAllPost();
	
	public Post savedPost(Integer postId,Integer userId) throws Exception;
	
	public Post likePost(Integer postId,Integer userId) throws Exception;
}
