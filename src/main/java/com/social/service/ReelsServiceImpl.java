package com.social.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.social.models.Reels;
import com.social.models.User;
import com.social.repository.ReelsRepository;
import com.social.repository.UserRepository;

@Service
public class ReelsServiceImpl implements ReelsService {
	
	@Autowired
	ReelsRepository reelRepository;
	
	@Autowired
	UserRepository userRepository;
 
	@Override
	public Reels createReels(Reels reel, User user) {
		
		Reels newReel = new Reels();
		newReel.setTitle(reel.getTitle());
		newReel.setUser(user);
		newReel.setVideo(reel.getVideo());
		
		Reels savedReel = reelRepository.save(newReel);
		
		return savedReel;
	}

	@Override
	public List<Reels> findAllReels() {
	 
		List<Reels> listOfReel = reelRepository.findAll();
		
		return listOfReel;
	}

	@Override
	public List<Reels> findReelById(Integer userId) throws Exception {
		
		userRepository.findById(userId);
		
		return reelRepository.findByUserId(userId);
	}

}
