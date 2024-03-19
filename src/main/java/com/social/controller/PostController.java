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
import org.springframework.web.bind.annotation.RestController;

import com.social.models.Post;
import com.social.response.ApiResponse;
import com.social.service.PostService;

@RestController
public class PostController {

	@Autowired
	PostService postService;

	@PostMapping("/post/user/{userid}")
	public ResponseEntity<Post> createPosts(@RequestBody Post post, @PathVariable Integer userid) throws Exception {
		Post createdPost = postService.createPost(post, userid);

		return new ResponseEntity<>(createdPost, HttpStatus.ACCEPTED);
	}

	@DeleteMapping("/post/{postid}/user/{userid}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postid, @PathVariable Integer userid)
			throws Exception {
		String message = postService.deletePost(postid, userid);
		ApiResponse resp = new ApiResponse(message, true);
		return new ResponseEntity<>(resp, HttpStatus.OK);
	}

	@GetMapping("/post/{postid}")
	public ResponseEntity<Post> findPostById(@PathVariable Integer postid) throws Exception {
		Post post = postService.findPostById(postid);
		return new ResponseEntity<>(post, HttpStatus.OK);
	}

	@GetMapping("/user/post/{userid}")
	public ResponseEntity<List<Post>> findPostByUserId(@PathVariable Integer userid) throws Exception {
		List<Post> lpost = postService.findPostByUserId(userid);
		return new ResponseEntity<>(lpost, HttpStatus.OK);
	}

	@GetMapping("/post")
	public ResponseEntity<List<Post>> findAllPostHandler() throws Exception {
		List<Post> lpost = postService.findAllPost();
		return new ResponseEntity<>(lpost, HttpStatus.OK);
	}

	@PutMapping("/post/save/{postid}/user/{userid}")
	public ResponseEntity<Post> savedPostHandler(@PathVariable Integer postid, @PathVariable Integer userid) throws Exception {
		Post post = postService.savedPost(postid, userid);
		return new ResponseEntity<>(post, HttpStatus.OK);
	}

	@PutMapping("/post/like/{postid}/user/{userid}")
	public ResponseEntity<Post> likePostHandler(@PathVariable Integer postid, @PathVariable Integer userid) throws Exception {
		Post post = postService.likePost(postid, userid);
		return new ResponseEntity<>(post, HttpStatus.OK);
	}
}
