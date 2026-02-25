package com.hotelbooking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hotelbooking.entity.LoginRequest;
import com.hotelbooking.entity.RegisterRequest;
import com.hotelbooking.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;
//	@GetMapping
//	public String login() {
//		return "Login user";
//	}
	
	@PostMapping("/register")
	public String register(@RequestBody RegisterRequest registerRequest) {
		
		return authService.register(registerRequest);
		
	}
	@PostMapping("/login")
	public String login(@RequestBody LoginRequest loginRequest) {
		return authService.login(loginRequest);
	}
	
}
