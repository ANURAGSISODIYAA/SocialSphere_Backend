package com.social.service;

import java.util.List;

import com.social.models.User;

public interface UserService {
   
	public User UserRegister(User user);
	
	public User findUserById(Integer id) throws Exception;
	
	public User findUserByEmail(String email);
	
	public User followUser(Integer id1, Integer id2) throws Exception;
	
	public User updateUser(User user,Integer id) throws Exception;
	
	public List<User> searchUser(String query);
	
}
