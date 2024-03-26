package com.social.service;

import java.util.List;

import com.social.models.Chat;
import com.social.models.User;

public interface ChatService {
          
      public Chat createChat(User reqUser, User user2);
      
      public Chat FindChatById(Integer chatId) throws Exception;
      
      public List<Chat> findUsersChat(Integer userId);
	
}
