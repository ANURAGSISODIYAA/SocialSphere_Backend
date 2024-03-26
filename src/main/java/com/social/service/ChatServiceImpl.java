package com.social.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.models.Chat;
import com.social.models.User;
import com.social.repository.ChatRepository;

@Service
public class ChatServiceImpl implements ChatService {
	
	@Autowired
	private ChatRepository chatRepository;

	@Override
	public Chat createChat(User reqUser, User user2) {
		
		Chat isExist = chatRepository.findChatByUsers(user2, reqUser);
		
		if(isExist!=null) {
			return isExist;
		}
		
		Chat chat = new Chat();
		chat.getUser().add(user2);
		chat.getUser().add(reqUser);
		chat.setTimeStamp(LocalDateTime.now());
		
		return chatRepository.save(chat);
	}

	@Override
	public Chat FindChatById(Integer chatId) throws Exception {
	
		Optional<Chat> chat = chatRepository.findById(chatId);
		if(chat.isEmpty()) {
			throw new Exception("Chat not found with this Id !!");
		}
		return chat.get();
	}

	@Override
	public List<Chat> findUsersChat(Integer userId) {
	
		return chatRepository.findByUserId(userId);
	}
          
}
