package com.hotelbooking.entity;

import lombok.Data;

@Data
public class HotelRequestDTO {

    private Long id;
    private String name;
    private String location;
    private double price;
    private boolean available;
}
