package com.hotelbooking.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.hotelbooking.entity.Hotel;
import com.hotelbooking.entity.Role;
import com.hotelbooking.entity.User;
import com.hotelbooking.repository.BookingRepository;
import com.hotelbooking.repository.HotelRepository;

@Service
public class HotelService {
	
	@Autowired
	private HotelRepository hotelRepository;
	@Autowired
	private BookingRepository bookingRepository;
	
	 public Hotel createHotel(Hotel hotel, User owner) {
		 
		 
		// Check if a hotel with the same name already exists
	      if (hotelRepository.findByName(hotel.getName()).isPresent()) {
	         throw new RuntimeException("A hotel with this name already exists!");
	      }
	        // Set the owner before saving
	        hotel.setOwner(owner);
	        return hotelRepository.save(hotel);
	    }
	//get
	public List<Hotel> getHotels(){
		
		return hotelRepository.findAll();
	}
	
	public List<Hotel> searchHotels(String location,LocalDate checkIn,LocalDate checkOut) {
	    
		if(!checkOut.isAfter(checkIn)) {
			throw new RuntimeException("Checkout must be after checkin");
		}
		List<Hotel> hotels = hotelRepository.findByLocation(location)
				.stream()
				.filter(hotel->bookingRepository
				.findByHotelIdAndCheckInBeforeAndCheckOutAfter(hotel.getId(), checkOut, checkIn)
				.stream().noneMatch(booking->!"CANCELLED".equalsIgnoreCase(booking.getStatus())))
			.toList();

		
	    if (hotels.isEmpty()) {
	        throw new RuntimeException("No hotels found in the specified location!");
	    }
	    
	    return hotels;
	}

	//delete
	public void deleteHotel(Long hotelId, User owner) {
	    Hotel hotel = hotelRepository.findById(hotelId)
	            .orElseThrow(() -> new RuntimeException("Hotel not found with ID: " + hotelId));

	    // Ensure only the owner can delete
	    if (!hotel.getOwner().getId().equals(owner.getId())) {
	        throw new RuntimeException("You are not authorized to delete this hotel!");
	    }

	    // Break the relationship
	    hotel.setOwner(null);
	    hotelRepository.save(hotel);

	    // Now delete the hotel
	    hotelRepository.delete(hotel);
	}

	
	
}
