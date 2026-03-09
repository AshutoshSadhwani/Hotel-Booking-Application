package com.hotelbooking.service;

import java.time.LocalDate;
import java.util.List;

import com.hotelbooking.entity.HotelRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hotelbooking.entity.Hotel;
import com.hotelbooking.entity.User;
import com.hotelbooking.repository.BookingRepository;
import com.hotelbooking.repository.HotelRepository;

@Service
public class HotelService {
	
	@Autowired
	private HotelRepository hotelRepository;
	@Autowired
	private BookingRepository bookingRepository;
	
	 public Hotel createHotel(HotelRequestDTO hotelRequestDTO, User owner) {
		 
		 
		// Check if a hotel with the same name already exists
	      if (hotelRepository.findByName(hotelRequestDTO.getName()).isPresent()) {
	         throw new RuntimeException("A hotel with this name already exists!");
	      }
		 // Convert DTO -> Entity
		 Hotel hotel = new Hotel();
		 hotel.setName(hotelRequestDTO.getName());
		 hotel.setLocation(hotelRequestDTO.getLocation());
		 hotel.setPrice(hotelRequestDTO.getPrice());
		 hotel.setAvailable(hotelRequestDTO.isAvailable());
	        // Set the owner before saving
	        hotel.setOwner(owner);
	        return hotelRepository.save(hotel);
	    }
	//get : only owner can get all hotels
	public List<Hotel> getHotels(){
		
		return hotelRepository.findAll();
	}

	// search:get
	public List<Hotel> searchHotels(String location) {
	    
//		if(!checkOut.isAfter(checkIn)) {
//			throw new RuntimeException("Checkout must be after checkin");
//		}
		List<Hotel> hotels = hotelRepository.findByLocationContainingIgnoreCase(location);
//				.stream()
//				.filter(hotel->bookingRepository
//				.findByHotelIdAndCheckInBeforeAndCheckOutAfter(hotel.getId(), checkOut, checkIn)
//				.stream().noneMatch(booking->!"CANCELLED".equalsIgnoreCase(booking.getStatus())))
//			.toList();

		
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
