package com.hotelbooking.repository;

import java.time.LocalDate;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotelbooking.entity.Booking;
import com.hotelbooking.entity.User;

public interface BookingRepository extends JpaRepository<Booking, Long>{

    List<Booking> findByHotelIdAndCheckInBeforeAndCheckOutAfter(Long hotelId, 
    		LocalDate checkOut, LocalDate checkIn);

    List<Booking> findByUser(User user);
}
