# Cinema Management System 🎟️🎬

A comprehensive desktop-based cinema operations and ticket booking management system built using Java (NetBeans) and MySQL. Developed as a collaborative academic project to streamline movie scheduling, ticket transactions, and administrative controls.

---

## 📌 Project Overview
The Cinema Management System is designed to digitize and manage core theater workflows. It handles employee/admin authentication, movie catalog management, dynamic showtime scheduling, interactive ticket booking, and real-time transaction processing.

---

## 💻 Tech Stack & Environment
* **Programming Language:** Java (NetBeans 8.2)
* **Database:** MySQL / DBeaver
* **Tools:** Java Swing (UI)

---

### 🎥 Project Demo

### 🔗 https://youtu.be/DJYe5yb83Kw

---

## 📱 Application features

- **User Ticket Booking System**
  
  - Login

    <img width="496" height="455" alt="image" src="https://github.com/user-attachments/assets/7ca5d207-38bf-492d-a144-2363739da5a3" />

  - Dashboard

    <img width="862" height="692" alt="image" src="https://github.com/user-attachments/assets/a211a790-2351-4017-a1e1-46233a3ea1bb" />

  - Ticket Booking
 
    <img width="1006" height="715" alt="image" src="https://github.com/user-attachments/assets/8bca9c20-bd7e-4569-b21e-ddb53b6a9b00" />

  - Update Movie
 
    <img width="577" height="512" alt="image" src="https://github.com/user-attachments/assets/411f8b3d-1d7c-45c6-8a5f-2ba70ed18ec9" />

- **CRUD Admin and Register System**

  - Login
 
    <img width="685" height="422" alt="image" src="https://github.com/user-attachments/assets/9770d0e6-7f95-4302-b063-f800fc630435" />

  - Dashboard
 
    <img width="1152" height="757" alt="image" src="https://github.com/user-attachments/assets/3f34e716-ab71-45fa-90f1-fdd03b8f1d3d" />

  - Admin Panel Movie System
 
    <img width="1045" height="700" alt="image" src="https://github.com/user-attachments/assets/73b5ca2f-e06a-4f87-a68f-ddfa15ef3d7e" />

  - Admin Panel Schedule System
 
    <img width="932" height="617" alt="image" src="https://github.com/user-attachments/assets/917f3425-03ee-4cd8-b739-78a0b8e1ccc0" />

  - Admin Panel Ticket Booking System
 
    <img width="921" height="615" alt="image" src="https://github.com/user-attachments/assets/b30d8a2b-8803-4833-a210-3b775ee88920" />

## 🛠️ System Architecture & Features

### 1. Key Modules
* **Authentication & Role-Based Access:** Secure login mechanism supporting distinct roles (Employee/Cashier and Admin) with credential verification and user registration.
* **Ticket Booking System:** Allows users to browse active movies, select showtimes, choose ticket categories (Adult/Child), calculate totals automatically (including tax), and generate transaction receipts.
* **Admin CRUD Panel:** Dedicated interface for administrators to manage movie catalogs (add, update, delete film details like title, genre, duration, and price), manage showtime schedules, and review overall booking data.

### 2. Database Structure (`MySQL`)
The relational database consists of 4 main tables:
* `akun`: Manages user credentials and roles.
* `film`: Stores movie details (title, genre, duration, price, and poster image path).
* `jadwal`: Links movies with specific screening dates and times.
* `transaksi`: Records ticket sales, quantity, total price, and timestamps linked to customer orders.


