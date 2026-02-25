package com.hotelbooking.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hotelbooking.entity.Hotel;
import com.hotelbooking.entity.Role;
import com.hotelbooking.entity.User;
import com.hotelbooking.helper.JwtUtil;
import com.hotelbooking.repository.UserRepository;
import com.hotelbooking.service.HotelService;

@RestController
@RequestMapping("/hotels")
public class HotelController {
	
	@Autowired
	private HotelService hotelService;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtUtil jwtUtil; // Add JWT utility to extract email
	
	
	@PostMapping
	public ResponseEntity<?> createHotel(
	        @RequestBody Hotel hotel,
	        @RequestHeader("Authorization") String token
	) {
	    try {
	    	
	        // Extract email from JWT token
	        String email = jwtUtil.extractUsername(token.substring(7));

	        // Fetch user from database
	        User owner = userRepository.findByEmail(email)
	                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

	        // Save hotel
	        Hotel savedHotel = hotelService.createHotel(hotel, owner);

	        return ResponseEntity.status(HttpStatus.CREATED).body("Hotel is created successfully");
	    } catch (RuntimeException e) {
	        return ResponseEntity.badRequest().body(e.getMessage()); // Return error message
	    }
	}
	
	@GetMapping
	public List<Hotel> getAllHotels(){
		return hotelService.getHotels();
	}

	 // 🔹 Search Hotels (Role-Based)
    @GetMapping("/search")
    public ResponseEntity<List<Hotel>> searchHotels(
            @RequestHeader("Authorization") String token,
            @RequestParam String location, 
    			@RequestParam LocalDate checkIn,
    			@RequestParam LocalDate checkOut){

        // Extract user from token
        String email = jwtUtil.extractUsername(token.substring(7));
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        // Perform role-based search
        List<Hotel> hotels = hotelService.searchHotels(location, checkIn,checkOut);

        return ResponseEntity.ok(hotels);
    }
	
	 //Delete a Hotel (Only Owners/Admins can delete)
    @DeleteMapping("/{hotelId}")
    public ResponseEntity<String> deleteHotel(
            @PathVariable Long hotelId,
            @RequestHeader("Authorization") String token) {

        // Extract email from JWT
        String email = jwtUtil.extractUsername(token.substring(7));

        // Fetch owner from DB
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with email: " + email));

        // 🛑 Check if the user is an OWNER or ADMIN
        if (!(user.getRole().equals(Role.OWNER) || user.getRole().equals(Role.ADMIN))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Only owners or admins can delete hotels!");
        }
        
        // Delete the hotel
        hotelService.deleteHotel(hotelId, user);

        return ResponseEntity.ok("Hotel deleted successfully!");
    }	
	

}
