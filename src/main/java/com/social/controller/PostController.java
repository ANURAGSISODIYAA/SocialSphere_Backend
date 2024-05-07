package com.social.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.social.models.Post;
import com.social.models.User;
import com.social.response.ApiResponse;
import com.social.service.PostService;
import com.social.service.UserService;

@RestController
public class PostController {

	@Autowired
	PostService postService;
	
	@Autowired
	UserService userService;

	@PostMapping("/api/posts")
	public ResponseEntity<Post> createPosts(@RequestBody Post post, @RequestHeader("Authorization") String jwt) throws Exception {
		
		User reqUser = userService.findUserByJwt(jwt);
		
		Post createdPost = postService.createPost(post, reqUser.getId());

		return new ResponseEntity<>(createdPost, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/post/{postid}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postid, @RequestHeader("Authorization") String jwt)
			throws Exception {
		User reqUser = userService.findUserByJwt(jwt);
		String message = postService.deletePost(postid, reqUser.getId());
		ApiResponse resp = new ApiResponse(message, true);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("/post/{postid}")
	public ResponseEntity<Post> findPostById(@PathVariable Integer postid) throws Exception {
		Post post = postService.findPostById(postid);
		return new ResponseEntity<>(post, HttpStatus.OK);
	}

	@GetMapping("/api/user/post/{userid}")
	public ResponseEntity<List<Post>> findPostByUserId(@PathVariable Integer userid) throws Exception {
		List<Post> lpost = postService.findPostByUserId(userid);
		return new ResponseEntity<>(lpost, HttpStatus.OK);
	}

	@GetMapping("/api/all/posts")
	public ResponseEntity<List<Post>> findAllPostHandler() throws Exception {
		List<Post> lpost = postService.findAllPost();
		return new ResponseEntity<>(lpost, HttpStatus.OK);
	}

	@PutMapping("/post/save/{postid}/user/{userid}")
	public ResponseEntity<Post> savedPostHandler(@PathVariable Integer postid, @RequestHeader("Authorization") String jwt) throws Exception {
		User reqUser = userService.findUserByJwt(jwt);
		Post post = postService.savedPost(postid, reqUser.getId());
		return new ResponseEntity<>(post, HttpStatus.OK);
	}

	@PutMapping("/api/post/like/{postid}")
	public ResponseEntity<Post> likePostHandler(@PathVariable Integer postid, @RequestHeader("Authorization") String jwt) throws Exception {
		User reqUser = userService.findUserByJwt(jwt);
		Post post = postService.likePost(postid, reqUser.getId());
		return new ResponseEntity<>(post, HttpStatus.OK);
	}
}
