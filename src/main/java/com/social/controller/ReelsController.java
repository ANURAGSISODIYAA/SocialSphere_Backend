package com.social.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.social.models.Reels;
import com.social.models.User;
import com.social.service.ReelsService;
import com.social.service.UserService;

@RestController
public class ReelsController {
        
	@Autowired
	ReelsService reelsService;
	
	@Autowired
	UserService userService;
	
	@PostMapping("/api/reels")
	public Reels createReel(@RequestBody Reels reel, @RequestHeader("Authorization")String jwt) {
		
		User user = userService.findUserByJwt(jwt);
		Reels newReel = reelsService.createReels(reel, user);
		
		return newReel;
		
	}
	
	@GetMapping("/api/reels")
	public List<Reels> findAllReels(){
		
		return reelsService.findAllReels();
	}
	
	@GetMapping("/api/reels/user/{userId}")
	public List<Reels> findReelByUser(@PathVariable Integer userId) throws Exception{
		
		User user = userService.findUserById(userId);
		
		return reelsService.findReelById(user.getId());
	}
	
	
}
