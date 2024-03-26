package com.social.service;

import java.util.List;

import com.social.exception.UserException;
import com.social.models.User;

public interface UserService {
   
	//public User UserRegister(User user);
	
	public User findUserById(Integer id) throws UserException;
	
	public User findUserByEmail(String email);
	
	public User followUser(Integer id1, Integer id2) throws UserException;
	
	public User updateUser(User user,Integer id) throws UserException;
	
	public List<User> searchUser(String query);
	
	public User findUserByJwt(String jwt);
	
}
