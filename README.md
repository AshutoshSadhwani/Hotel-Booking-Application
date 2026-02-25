# Hotel Booking Application

## Overview
The Hotel Booking Application is a backend service built using Spring Boot that enables users to list, search, and book hotel rooms. It includes authentication and authorization mechanisms to ensure secure access and role-based functionalities.

## Features

### 1. User Management
- Secure authentication and authorization using JWT (JSON Web Tokens).
- Role-based access control for **Owners** and **Users**.

### 2. Hotel Room Listings
- Owners can **create, update, and delete** their hotel room listings.
- Each listing includes details like price and availability.

### 3. Search and Filter
- Users can search for available hotel rooms based on **check-in and check-out dates**.
- Additional filters include price , location.

### 4. Room Booking
- Users can reserve available hotel rooms.
- The system manages room availability to prevent double booking.
- Booking details are stored in the database for future reference.

## Technologies Used

### Backend
- **Spring Boot** – REST API development
- **Spring Security** – Authentication and authorization with JWT

### Database
- **MySQL** – Relational database for storing user accounts, hotel rooms, and bookings
- **Spring Data JPA** – ORM for interacting with the database

### Security
- **JWT (JSON Web Token)** – Token-based authentication
- **Role-based access control**:
  - **Owner**: Can list and manage hotels
  - **User**: Can search and book rooms



