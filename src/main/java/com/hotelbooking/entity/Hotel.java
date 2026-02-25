package com.hotelbooking.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Hotel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String location;
	private double price;
	private boolean available;
	
//	@JsonManagedReference  // 🔹 Prevents infinite recursion
	@ManyToOne
	@JoinColumn(name = "owner_id", nullable = false)
	@JsonIgnoreProperties({"username", "password",
		"email", "role", "hotels"}) // 🔹 Ignore unnecessary fields
	private User owner;

//	mappedBy means
//	The real foreign key is managed by the field named hotel in the Booking entity (@ManyToOne Hotel hotel).
//	So JPA does not create an extra join table for this relation.
//	It links Hotel -> bookings using the hotel_id column from Booking.
	
	@JsonIgnore
	@OneToMany(mappedBy="hotel")  
	private List<Booking> bookings;
	
	
	
}
