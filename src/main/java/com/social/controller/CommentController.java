package com.social.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.social.models.Comment;
import com.social.models.User;
import com.social.service.CommentService;
import com.social.service.UserService;

@RestController
public class CommentController {
           
	@Autowired
	CommentService commentService;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/api/comment/post/{pid}")
	public Comment createComment(@RequestBody Comment comment,
			@RequestHeader("Authorization") String jwt,@PathVariable Integer pid) throws Exception {
		
		User user = userService.findUserByJwt(jwt);
		
		Comment newComment = commentService.createComment(comment, pid, user.getId());
		return newComment;
		
	}
	
	@PutMapping("/api/comment/like/{cid}")
	public Comment likeComment(
			@RequestHeader("Authorization") String jwt,@PathVariable Integer cid) throws Exception {
		
		User user = userService.findUserByJwt(jwt);
		
		Comment LikedComment = commentService.likeComment(cid, user.getId());
		
		return LikedComment;
		
	}
	
}
