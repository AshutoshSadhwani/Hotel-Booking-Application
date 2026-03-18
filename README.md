# Hotel Booking Application

## Overview
The Hotel Booking Application is a backend system developed using Spring Boot that allows users to search, list, and securely book hotel rooms. The application focuses on preventing double bookings, ensuring secure access, and enforcing strict role-based operations to avoid misuse or booking-related fraud.

## Features

### 1. User Management
- Secure authentication using JWT (JSON Web Tokens)
- Role-based access control for:
- User
  - Search hotels
  - Book rooms
  - View & cancel their own bookings
- Owner
  - Create and manage their hotel listings
- Admin
  - View and manage all hotel listings
  - Cannot perform bookings (restricted intentionally)

### 2. Hotel Room Listings
- Owners can:
  - Create new hotel room listings
  - Update room details (price, availability, etc.)
  - Delete their listings
  - Each listing contains structured data such as:
      Price
      Location
      Availability status

### 3. Search and Filter
- Users can search for available hotel rooms based on **check-in and check-out dates**.

### 4. . Booking Management (Core Feature)
- Users can reserve hotel rooms through secure booking APIs
- System ensures:

  - ✅ No double booking using availability checks
  - ✅ Booking validation before confirmation

- Users can:
  - View their bookings
  - Cancel their own bookings

- ⚠️ Security Design Decision:
  - Booking APIs are restricted only to users
  - Owners and Admins cannot create or manipulate bookings
  - This prevents potential booking fraud or misuse

## Technologies Used

### Backend
- **Spring Boot** – REST API development
- **Spring Security** – Authentication and authorization with JWT

### Database
- **PostgreSQL** – Relational database for storing user accounts, hotel rooms, and bookings
- **Spring Data JPA** – ORM for interacting with the database
