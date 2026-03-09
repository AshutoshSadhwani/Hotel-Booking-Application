package com.hotelbooking.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hotelbooking.entity.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, Long>{

	
//	@Query("select h from Hotel h where "
//			+ "lower(h.location) like lower(concat('%',:location,'%')) and  h.available=true")
//	List<Hotel> findByLocation(String location);


	//ContainingIgnoreCase is actually an inbuilt feature of Spring Data JPA.
//	containingIgnoreCase== SELECT * FROM hotel
//	WHERE LOWER(location) LIKE LOWER('%value%');

	List<Hotel> findByLocationContainingIgnoreCase(String location);

	Optional<Hotel> findByName(String name);
}
