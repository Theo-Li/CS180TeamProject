# CS18000 Marketplace Project — Phase 1  


---

## 📌 Overview  
This phase implements the backend of a multithreaded Marketplace system in Java. Users can register, manage items, send messages, and make payments. The design ensures thread-safe access through atomic counters and synchronized methods, supporting multiple clients via a single server.

---

## ✅ Core Functionality  

### 1. User Accounts  
- Register with a unique username and password.  
- Each user is assigned a unique `userID` using atomic counters.  
- Balances are tracked and adjustable.  
- Buyers and sellers are handled by the same `User` class.  

### 2. Item Listings  
- Each item has a `name`, `price`, `pictureFilename`, and `sellerID`.  
- Items are assigned unique `itemID`s via atomic counters.  
- Users can add or remove items from their list.  

### 3. Messaging System  
- Buyers can send messages to sellers.  
- Messages include `senderID`, `receiverID`, and message text.  
- `MessageManager` handles message storage and retrieval.  

### 4. Payment Processing  
- Payment records include `buyerID`, `sellerID`, `amount`, `timestamp`, and `status`.  
- `PaymentManager` updates user balances and tracks transaction states.  
- `PaymentStatus` enum includes `PENDING`, `COMPLETED`, and `FAILED`.  

---

## 🧵 Thread Safety & Concurrency  
- `AtomicInteger` is used to generate unique IDs (`userID`, `itemID`, `paymentID`).  
- All manager classes (e.g., `UserManager`, `ItemManager`) use `synchronized` methods.  
- Designed for multi-client environments connecting to a single server.  

---

## 💡 Design Highlights  
- **Modular architecture**: Responsibilities are divided across manager classes.  
- **Encapsulation**: Internal logic and data structures are hidden behind clean interfaces.  
- **Interface use**: Interfaces like `IUser`, `IItem`, and `IMessage` standardize functionality.  

---



## 📁 Files Included  
- `User.java` – User definition and methods  
- `UserHandler.java` – User handling logic 
- `UserTest.java` – Unit tests for `User` class using JUnit  
- `Item.java` – Item structure and fields  
- `Message.java` / `IMessage.java` – Messaging system  
- `Marketplace.java` – Entry point, main backend integration  
- `README.txt` – This documentation  

---

## 🧪 Testing Methodology  
Tests cover:  
- User creation, login, balance updates  
- Item add/remove methods  
- Message sending and retrieval  
- Payment record creation and balance changes  
- Thread safety simulated via concurrent access  
- Unit tests written using **JUnit** in `UserTest.java`

---




