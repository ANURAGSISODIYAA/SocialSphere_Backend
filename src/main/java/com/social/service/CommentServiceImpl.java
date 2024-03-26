package com.social.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.models.Comment;
import com.social.models.Post;
import com.social.models.User;
import com.social.repository.CommentRepository;
import com.social.repository.PostRepository;

@Service
public class CommentServiceImpl implements CommentService{
	
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	PostService postService;
	
	@Autowired
	PostRepository postRepository;
	
	@Autowired
	UserService userService;

	@Override
	public Comment createComment(Comment comment, Integer pid, Integer uid) throws Exception {
	     
		User user = userService.findUserById(uid);
		Post post = postService.findPostById(pid);
		
		comment.setUser(user);
		comment.setContent(comment.getContent());
		comment.setCreatedAT(LocalDateTime.now());
		
		Comment savedComment = commentRepository.save(comment);
		
		post.getComments().add(savedComment);
		postRepository.save(post);
		
		return savedComment;
	}

	@Override
	public Comment likeComment(Integer cid, Integer uid) throws Exception {
		Comment comment = findCommentByid(cid);
		User user = userService.findUserById(uid);
		
		if(!comment.getLiked().contains(user)) {
			comment.getLiked().add(user);
		}else {
			comment.getLiked().remove(user);
		}
		return commentRepository.save(comment);
	}

	@Override
	public Comment findCommentByid(Integer cid) throws Exception {
		Optional<Comment> comment = commentRepository.findById(cid);
		
		if(comment.isEmpty()) {
			throw new Exception("Cooment not found !!!");
		}
		
		return comment.get();
	}

}
