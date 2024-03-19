package com.social.service;


import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.models.Post;
import com.social.models.User;
import com.social.repository.PostRepository;
import com.social.repository.UserRepository;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	PostRepository postRepository;

	@Autowired
	UserService userService;
	
	@Autowired
	UserRepository userRepository;

	@Override

	public Post createPost(Post post, Integer userId) throws Exception {

		User user = userService.findUserById(userId);
		Post newPost = new Post();
		newPost.setCaption(post.getCaption());
		newPost.setImage(post.getImage());
		newPost.setVideo(post.getVideo());
	    newPost.setCreatedAt(new Date());
		newPost.setUser(user);

		Post savedPost = postRepository.save(newPost);
		return savedPost;
	}

	@Override
	public String deletePost(Integer postId, Integer userId) throws Exception {
		
	    Post post = findPostById(postId);
	    User user = userService.findUserById(userId);
	    
	    if(post.getUser().getId()!=user.getId()) {
	    	throw new Exception("Yo cant delete other User Post !!!");
	    }
	    
	    postRepository.delete(post);
	    
		return "Post deleted Succesfully";
	}

	@Override
	public Post findPostById(Integer postId) throws Exception {
		Optional<Post> post = postRepository.findById(postId);
		if (post.isEmpty()) {
			throw new Exception("Post Not Found !!!");
		}
		return post.get();
	}
    
	@Override
	public List<Post> findPostByUserId(Integer userId) throws Exception {
		
		return postRepository.findPostByUserid(userId);
	}
	

	@Override
	public List<Post> findAllPost() {
		return postRepository.findAll();
	}

	@Override
	public Post savedPost(Integer postId, Integer userId) throws Exception {
		Post post = findPostById(postId);
		User user = userService.findUserById(userId);
		
		if(user.getSavedPost().contains(post)){
			user.getSavedPost().remove(post);		
		}else {
			user.getSavedPost().add(post);
		}
		userRepository.save(user);
		return post;
	
	}

	@Override
	public Post likePost(Integer postId, Integer userId) throws Exception {
		Post post = findPostById(postId);
		User user = userService.findUserById(userId);
		
		// post.getLike() will return list of user and 
		// then we can see, remove, add users in it
		if(post.getLike().contains(user)) {
			post.getLike().remove(user);		
		}else {
			post.getLike().add(user);
		}
	   
		return postRepository.save(post);
	}

}
