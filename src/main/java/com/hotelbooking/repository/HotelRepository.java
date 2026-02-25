package com.hotelbooking.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.hotelbooking.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long>{

	
//	@Query("select h from Hotel h where "
//			+ "lower(h.location) like lower(concat('%',:location,'%')) and  h.available=true")
	List<Hotel> findByLocation(String location);	
	
	Optional<Hotel> findByName(String name);
}
