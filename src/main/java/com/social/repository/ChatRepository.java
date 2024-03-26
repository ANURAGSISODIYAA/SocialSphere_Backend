package com.social.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.social.models.Chat;
import com.social.models.User;

public interface ChatRepository extends JpaRepository<Chat, Integer>{
	 
	public List<Chat> findByUserId(Integer userId);
	
	@Query("SELECT c FROM Chat c WHERE :user MEMBER OF c.user AND :reqUser MEMBER OF c.user")
	public Chat findChatByUsers(@Param("user") User user, @Param("reqUser") User reqUser);




}
