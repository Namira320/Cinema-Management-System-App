# Cinema Management System 🎟️🎬

A comprehensive desktop-based cinema operations and ticket booking management system built using Java (NetBeans) and MySQL. Developed as a collaborative academic project to streamline movie scheduling, ticket transactions, and administrative controls.

---

## 📌 Project Overview
The Cinema Management System is designed to digitize and manage core theater workflows. It handles employee/admin authentication, movie catalog management, dynamic showtime scheduling, interactive ticket booking, and real-time transaction processing.

---

## 📱 Application features

- Login

  <img width="496" height="455" alt="image" src="https://github.com/user-attachments/assets/7ca5d207-38bf-492d-a144-2363739da5a3" />

- Dashboard

  <img width="862" height="692" alt="image" src="https://github.com/user-attachments/assets/a211a790-2351-4017-a1e1-46233a3ea1bb" />





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

---

## 💻 Tech Stack & Environment
* **Programming Language:** Java (NetBeans 8.2)
* **Database:** MySQL / DBeaver
* **Tools:** Java Swing (UI)

