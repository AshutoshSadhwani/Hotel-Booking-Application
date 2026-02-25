package com.hotelbooking.entity;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

	@NotBlank(message = "username cannot be empty")
	@Size(min=4,message = "username must be atleast 4 char long")
	private String username;
	
	@Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be empty")
	private String email;
	
	@NotBlank(message = "Password is required")
	private String password;

	private String role;
}
