package com.hotelbooking.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.hotelbooking.entity.Hotel;
import com.hotelbooking.entity.HotelRequestDTO;
import com.hotelbooking.repository.HotelRepository;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hotelbooking.entity.Booking;
import com.hotelbooking.service.BookingService;

@RestController
@RequestMapping("/bookings")
@Tag(name = "Booking Apis",description = "THIS IS ONLY DONE BY THE USERS ITSELF NOT BY OWNER OR ADMIN TO AVOID THE BOOKING SCAM(book hotels,get the booking,delete bookings)")
public class BookingController {

	@Autowired
	private BookingService bookingService;
	@Autowired
	private HotelRepository hotelRepository;
	
	   @PostMapping("/book")
	    public ResponseEntity<String> bookHotel(
	            @RequestHeader("Authorization") String token,
	            @RequestParam Long hotelId,
	            @RequestParam LocalDate checkIn,
	            @RequestParam LocalDate checkOut) {
	        return ResponseEntity.ok(bookingService.bookHotel(token.substring(7), hotelId, checkIn, checkOut));
	    }

		@GetMapping("/getallhotels")
	  	public List<HotelRequestDTO> getAllHotels(){

		   List<Hotel> hotels=hotelRepository.findAll();
		   List<HotelRequestDTO> response=new ArrayList<>();

		   for(Hotel h:hotels){
			   HotelRequestDTO hotelRequestDTO=new HotelRequestDTO();
			   hotelRequestDTO.setId(h.getId());
			   hotelRequestDTO.setName(h.getName());
			   hotelRequestDTO.setPrice(h.getPrice());
			   hotelRequestDTO.setLocation(h.getLocation());
			   hotelRequestDTO.setAvailable(h.isAvailable());

			   response.add(hotelRequestDTO);
		   }

		   return response;
		};


	    @GetMapping
	    public ResponseEntity<List<Booking>> getUserBookings(@RequestHeader("Authorization") String token) {
	    	
	    		
	        return ResponseEntity.ok(bookingService.getUserBookings(token.substring(7)));
	    }
	    
	    @DeleteMapping("/cancel/{bookingId}")
	    public ResponseEntity<String> cancelBooking(
	            @RequestHeader("Authorization") String token,
	            @PathVariable Long bookingId) {
		   return ResponseEntity.ok(bookingService.cancelBooking(token.substring(7), bookingId));
	    }
}
