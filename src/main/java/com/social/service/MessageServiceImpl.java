package com.social.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.models.Chat;
import com.social.models.Message;
import com.social.models.User;
import com.social.repository.ChatRepository;
import com.social.repository.MessageReposiotry;

@Service
public class MessageServiceImpl implements MessageService {

	@Autowired
	private MessageReposiotry messageReposiotry;

	@Autowired
	private ChatService chatService;
	
	@Autowired
	private ChatRepository chatRepository;

	@Override
	public Message createMessage(User user, Integer chatId, Message msg) throws Exception {

		Message message = new Message();

		Chat chat = chatService.FindChatById(chatId);

		message.setChat(chat);
		message.setContent(msg.getContent());
		message.setImage(msg.getImage());
		message.setUser(user);
		message.setTimeStamp(LocalDateTime.now());
        
		Message savedMessage = messageReposiotry.save(message);
		
		chat.getMessage().add(savedMessage);
		chatRepository.save(chat);
		return savedMessage;
	}

	@Override
	public List<Message> findChatMessages(Integer chatId) throws Exception {
         
		 chatService.FindChatById(chatId);
		 
		return messageReposiotry.findByChatId(chatId);
	}

}
