package com.hotelbooking.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hotelbooking.entity.LoginRequest;
import com.hotelbooking.entity.LoginResponse;
import com.hotelbooking.entity.RegisterRequest;
import com.hotelbooking.entity.Role;
import com.hotelbooking.entity.User;
import com.hotelbooking.helper.JwtUtil;
import com.hotelbooking.repository.UserRepository;

@Service
public class AuthService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private JwtUtil jwtUtil;
	
	public String register(RegisterRequest register) {
//		User user= new User(null, register.getUsername(),
//				passwordEncoder.encode(register.getPassword()), 
//				register.getEmail(), Role.valueOf(register.getRole()));
		User user = new User();
		user.setUsername(register.getUsername());
		user.setPassword(passwordEncoder.encode(register.getPassword()));
		user.setEmail(register.getEmail());
		user.setRole(Role.valueOf(register.getRole()));

		userRepository.save(user);
		return "User registered successfully!";
	}
	
	public String login(LoginRequest request) {
		User user=userRepository.findByEmail(request.getEmail())
				.orElseThrow(()->new RuntimeException("Invalid email or password"));
		if(passwordEncoder.matches(request.getPassword(),user.getPassword())) {
			return jwtUtil.generateToken(user.getEmail());
		}
		else {
			throw new RuntimeException("Invalid Credentials");
		}
	}
}
