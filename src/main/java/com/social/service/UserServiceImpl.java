package com.social.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.models.User;
import com.social.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    
	@Autowired
	UserRepository userRepository;
	
	@Override
	public User UserRegister(User user) {
		
		User newUser = new User();
		newUser.setId(user.getId());
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setEmail(user.getEmail());
		newUser.setPassword(user.getPassword());
		
		User savedUser = userRepository.save(newUser);
		
		return savedUser;
	}

	@Override
	public User findUserById(Integer id) throws Exception {
		
		Optional<User> user = userRepository.findById(id);
		if(user.isEmpty()) {
			throw new Exception("User Not Found !!!");
		}
		User user1 = user.get();
		return user1;
	}

	@Override
	public User findUserByEmail(String email) {
		User user = userRepository.findByEmail(email);
		return user;
	}

	@Override
	public User followUser(Integer id1, Integer id2) throws Exception {
		User user1 = findUserById(id1);
		User user2 = findUserById(id2);
		
		user2.getFollowers().add(user1.getId());
		user1.getFollowings().add(user2.getId());
		
		userRepository.save(user1);
		userRepository.save(user2);
		
		return user1;
	}

	@Override
	public User updateUser(User user,Integer id) throws Exception {

		Optional<User> user1 = userRepository.findById(id);
		User oldUser = user1.get();
		if(user1.isEmpty()) {
			throw new Exception("User Not Found");
		}
		if(oldUser.getFirstName()!=null) {
			oldUser.setFirstName(user.getFirstName());
		}
		if(oldUser.getLastName()!=null) {
			oldUser.setLastName(user.getLastName());
		}
		if(oldUser.getPassword()!=null) {
			oldUser.setPassword(user.getPassword());
		}
		
		User updatedUser = userRepository.save(oldUser);
		return updatedUser;
	}

	@Override
	public List<User> searchUser(String query) {
	
		return userRepository.searchUser(query);
	}

}
