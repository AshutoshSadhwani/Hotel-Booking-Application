package com.hotelbooking.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hotelbooking.entity.Booking;
import com.hotelbooking.entity.Hotel;
import com.hotelbooking.entity.User;
import com.hotelbooking.helper.JwtUtil;
import com.hotelbooking.repository.BookingRepository;
import com.hotelbooking.repository.HotelRepository;
import com.hotelbooking.repository.UserRepository;

@Service
public class BookingService {
	
	@Autowired
	private BookingRepository bookingRepository;
	@Autowired
	private HotelRepository  hotelRepository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JwtUtil jwtUtil;
	
	
	@Transactional
	public String bookHotel(String token, Long hotelId, LocalDate checkIn, LocalDate checkOut) {
		
		  if (checkOut.isEqual(checkIn)) {
		        return "checkOut cannot be the same as checkIn pls stay atleast one night or pay the one night bill for sameday";
		    }
		    if (checkOut.isBefore(checkIn)) {
		        return "checkOut must be after checkIn.";
		    }
		
	    // Extract user ID from JWT
	    String username = jwtUtil.extractUsername(token);
	    User user = userRepository.findByEmail(username)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    // Check if hotel exists
	    Hotel hotel = hotelRepository.findById(hotelId)
	            .orElseThrow(() -> new RuntimeException("Hotel not found"));

	    // ✅ Exclude canceled bookings when checking availability
	    List<Booking> existingBookings = bookingRepository.findByHotelIdAndCheckInBeforeAndCheckOutAfter(hotelId, checkOut, checkIn)
	            .stream()
	            .filter(booking -> !booking.getStatus().equalsIgnoreCase("CANCELLED")) // Ignore canceled bookings
	            .toList();

	    if (!existingBookings.isEmpty()) {
	        return "Room is already booked for these dates.";
	    }

	    // Create new booking
	    Booking booking = new Booking(null, user, hotel, checkIn, checkOut, "CONFIRMED");
//	    hotel.setAvailable(false);
	    bookingRepository.save(booking);

	    return "Room booked successfully!";
	}

   
    
    public List<Booking> getUserBookings(String token) {
        String username = jwtUtil.extractUsername(token);
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return bookingRepository.findByUser(user);
    }

    
    public String cancelBooking(String token, Long bookingId) {
        String username = jwtUtil.extractUsername(token);
        User user = userRepository.findByEmail(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (!booking.getUser().getId().equals(user.getId())) {
            return "You can only cancel your own bookings!";
        }

        booking.setStatus("CANCELLED");
        bookingRepository.save(booking);
        return "Booking cancelled!";
    }


   



}
