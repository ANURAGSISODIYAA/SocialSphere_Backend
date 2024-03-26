package com.social.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.social.config.JwtProvider;
import com.social.models.User;
import com.social.repository.UserRepository;
import com.social.request.LoginRequest;
import com.social.response.AuthResponse;
import com.social.service.CustomUserDetailService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private CustomUserDetailService customUserDetail;
    
	@PostMapping("/signup")
	public AuthResponse signUp(@RequestBody User user) throws Exception {
		
		User isExit  = userRepository.findByEmail(user.getEmail());
		if(isExit!=null) {
			throw new Exception("User with this email already Exist");
		}
		
		User newUser = new User();
		newUser.setFirstName(user.getFirstName());
		newUser.setLastName(user.getLastName());
		newUser.setGender(user.getGender());
		newUser.setEmail(user.getEmail());
		newUser.setPassword(passwordEncoder.encode(user.getPassword()));
		
		User savedUser = userRepository.save(newUser);
		
		Authentication auth = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
		
		String token = JwtProvider.generateToken(auth);
		
		AuthResponse authResponse = new AuthResponse(token,"Registration Success !!");
		
		return authResponse;
	}
	
	@PostMapping("/sigin")
	public AuthResponse signIn(@RequestBody LoginRequest loginRequest) {
		  
		Authentication auth = authentication(loginRequest.getEmail(), loginRequest.getPassword());
		
		String token = JwtProvider.generateToken(auth);
		
		AuthResponse authResponse = new AuthResponse(token,"Login Success !!");
		
		return authResponse;
		}


	private Authentication authentication(String email, String password) {
		UserDetails userDeatils = customUserDetail.loadUserByUsername(email);
		
		if(userDeatils==null) {
			throw new BadCredentialsException("user not found !!");
		}
		
		if(!passwordEncoder.matches(password, userDeatils.getPassword())) {
			throw new BadCredentialsException("Incorreect password !!");
		}
		return new UsernamePasswordAuthenticationToken(userDeatils, null ,userDeatils.getAuthorities());
	
	}
	 
	}


