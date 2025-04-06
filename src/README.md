# 💼 CS18000 Marketplace Project — Phase 1

---

## 📌 Overview

This phase implements the backend of a multithreaded Marketplace system in Java, allowing users to register, manage items, send messages, and make payments. The design supports multiple clients via a single server and ensures thread-safe access through atomic counters and synchronized methods.

---

## ✅ Core Functionality

### 1. User Accounts
- Register with a unique username and password  
- Atomic counters assign each user a unique `userID`  
- Balances are tracked and adjustable  
- The same `User` class handles buyers and sellers  

### 2. Item Listings
- Every item has a `sellerID`, `name`, `price`, and `pictureFilename`  
- Atomic counters assign unique `itemID`s  
- Users can add or remove items from their listings  

### 3. Messaging System
- Buyers can send messages to sellers  
- Each message includes `senderID`, `receiverID`, and message text  
- `MessageManager` handles message storage and retrieval  

### 4. Payment Processing
- Payment records include `buyerID`, `sellerID`, `amount`, `timestamp`, and `status`  
- `PaymentManager` updates user balances and monitors transaction states  
- `PaymentStatus` enum tracks `PENDING`, `COMPLETED`, and `FAILED` transactions  

---

## 🧵 Thread Safety & Concurrency

- `AtomicInteger` generates unique IDs (`userID`, `itemID`, `paymentID`)  
- All manager classes (e.g., `UserManager`, `ItemManager`) use `synchronized` methods  
- Designed for multi-client environments connecting through one server  

---

## 📁 Files Included

- `User.java` – User definition and methods  
- `UserHandler.java` – User handling logic  
- `test.UserTest.java` – Unit tests for `User` class using JUnit  
- `Item.java` – Item structure and fields  
- `Message.java` / `IMessage.java` – Messaging system  
- `Marketplace.java` – Entry point, main backend integration  
- `README.md` – This documentation  

---

## 🧪 Testing Methodology

Tests cover:
- User creation, login, and balance updates  
- Item addition and removal  
- Messaging functionality  
- Payment processing and balance adjustment  
- Thread safety with simulated concurrent access  
- Unit testing implemented via **JUnit** in `test.UserTest.java`  

---

