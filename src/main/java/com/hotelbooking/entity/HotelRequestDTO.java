package com.hotelbooking.entity;

import lombok.Data;

@Data
public class HotelRequestDTO {
    private String name;
    private String location;
    private double price;
    private boolean available;
}
