package com.social.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.config.JwtProvider;
import com.social.exception.UserException;
import com.social.models.User;
import com.social.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    
	@Autowired
	UserRepository userRepository;
	
	@Override
	public User findUserById(Integer id) throws UserException {
		
		Optional<User> user = userRepository.findById(id);
		if(user.isEmpty()) {
			throw new UserException("User Not Found !!!");
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
	public User followUser(Integer reqUserId, Integer id2) throws UserException {
		User reqUser = findUserById(reqUserId);
		User user2 = findUserById(id2);
		
		user2.getFollowers().add(reqUser.getId());
		reqUser.getFollowings().add(user2.getId());
		
		userRepository.save(reqUser);
		userRepository.save(user2);
		
		return reqUser;
	}

	@Override
	public User updateUser(User user,Integer id) throws UserException {

		Optional<User> user1 = userRepository.findById(id);
		User oldUser = user1.get();
		if(user1.isEmpty()) {
			throw new UserException("User Not Found");
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
		if(oldUser.getGender()!=null) {
			oldUser.setGender(user.getGender());
		}
		
		User updatedUser = userRepository.save(oldUser);
		return updatedUser;
	}

	@Override
	public List<User> searchUser(String query) {
	
		return userRepository.searchUser(query);
	}

	@Override
	public User findUserByJwt(String jwt) {
		
		String email = JwtProvider.getEmailFromJwtToken(jwt);
		
		User user = userRepository.findByEmail(email);
		
		return user;
	}

}
