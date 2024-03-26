package com.social.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.social.models.Reels;
import com.social.models.User;

public interface ReelsRepository extends JpaRepository<Reels,Long> {
	
	public List<Reels> findByUserId(Integer userId);

}
