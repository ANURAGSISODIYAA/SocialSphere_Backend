package com.social.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.social.exception.UserException;
import com.social.models.User;
import com.social.repository.UserRepository;
import com.social.service.UserService;

@RestController
public class UserController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	@GetMapping("/api/user")
	public List<User> getUser() {

		// getting data through jpaRepo from our database
		List<User> users = userRepository.findAll();
		return users;
	}

	@GetMapping("/api/user/{userId}")
	public User getUserById(@PathVariable("userId") Integer id) throws UserException {

		User user = userService.findUserById(id);
		return user;
	}

	@PutMapping("/api/users")
	public User updateUser(@RequestBody User user, @RequestHeader("Authorization") String jwt) throws UserException {

		User reqUser = userService.findUserByJwt(jwt);
		User updatedUser = userService.updateUser(user, reqUser.getId());
		return updatedUser;
	}

	@PutMapping("/api/user/follow/{userId2}")
	public User FollowUserHandler(@RequestHeader("Authorization") String jwt, @PathVariable Integer userId2)
			throws UserException {
		User reqUser = userService.findUserByJwt(jwt);
		User user = userService.followUser(reqUser.getId(), userId2);
		return user;
	}

	@GetMapping("/api/user/search")
	public List<User> SearchUserHandler(@RequestParam("query") String query) {

		List<User> user = userService.searchUser(query);
		return user;
	}

	@GetMapping("/api/user/profile")
	public User getUserFromToken(@RequestHeader("Authorization") String jwt) {

		User user = userService.findUserByJwt(jwt);

		user.setPassword(null);

		return user;
	}

}
