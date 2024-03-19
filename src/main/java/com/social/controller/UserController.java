package com.social.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.social.models.User;
import com.social.repository.UserRepository;
import com.social.service.UserService;

@RestController
public class UserController {
    
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/users")
	public User createUser(@RequestBody User user) {
		
		User saveduser = userService.UserRegister(user);
		return saveduser;
	}
	
	@GetMapping("/api/user")
	public List<User> getUser() {
		
		// getting data through jpaRepo from our database
		List<User> users = userRepository.findAll();
		return users;
	}
	
	@GetMapping("/api/user/{userId}")
	public User getUserById(@PathVariable("userId") Integer id) throws Exception {
		
		User user = userService.findUserById(id);
		return user;
	}
  
	@PutMapping("/api/users/{userId}")
	public User updateUser(@RequestBody User user,@PathVariable Integer userId) throws Exception {
		User updatedUser = userService.updateUser(user, userId);
		return updatedUser;
	}
    
	@PutMapping("/api/user/follow/{userId1}/{userId2}")
	public User FollowUserHandler(@PathVariable Integer userId1,@PathVariable Integer userId2) throws Exception {
		User user = userService.followUser(userId1, userId2);
		return user;
	}
	
	
	@GetMapping("/api/user/search")
	public List<User> SearchUserHandler(@RequestParam("query")String keyword) {
		
		List<User> user = userService.searchUser(keyword);
		return user;
	}

	
}
