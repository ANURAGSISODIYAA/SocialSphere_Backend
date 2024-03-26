package com.social.service;

import com.social.models.Comment;

public interface CommentService {
          
	public Comment createComment(Comment comment , Integer pid, Integer uid) throws Exception;
	
	public Comment likeComment(Integer cid, Integer uid) throws Exception;
	
	public Comment findCommentByid(Integer cid) throws Exception;
}
