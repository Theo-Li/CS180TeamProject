# 💼 CS18000 Marketplace Project — Phase 1

---

## 📌 Overview

This phase implements the backend of a multithreaded Marketplace system in Java, allowing users to register, manage items, send messages, and make payments. The design supports multiple clients via a single server and ensures thread-safe access through atomic counters and synchronized methods.

---

## ✅ Core Functionality

### 1. 👤 User Accounts
- Users register with a **unique username and password**
- Each user receives a **unique userID** via `AtomicInteger`
- Users maintain and update their **account balances**
- The `User` class supports both buyers and sellers

### 2. 📦 Item Listings
- Each `Item` includes: `sellerID`, `name`, `price`, `pictureFilename`
- Each item gets a **unique itemID**
- Users can **add/remove** items from their listings via `ItemManager`

### 3. 💬 Messaging System
- Buyers can message sellers using `MessageManager`
- Each `Message` includes: `senderID`, `receiverID`, `text`
- Thread-safe message **storage and retrieval**

### 4. 💳 Payment Processing
- Payment records store: `buyerID`, `sellerID`, `amount`, `timestamp`, `status`
- `PaymentManager` handles **balance updates** and **transaction status**
- `PaymentStatus` enum defines: `PENDING`, `COMPLETED`, `FAILED`

---

## 🧵 Thread Safety & Concurrency
- `AtomicInteger` is used to generate unique IDs: `userID`, `itemID`, `paymentID`
- All manager classes (`UserManager`, `ItemManager`, etc.) use **synchronized methods**
- Supports **multi-client concurrency** through a centralized server model

---

## 📁 Files Included

### 📂 Main Classes
- `main/Marketplace.java` – Project entry point and backend integration

### 👥 User Management
- `User.java` – User object definition and core logic
- `IUser.java` – User interface specification
- `UserManager.java` – Logic for managing users
- `IUserManager.java` – Interface for user management

### 📦 Item Management
- `Item.java` – Item object and fields
- `IItem.java` – Interface for items
- `ItemManager.java` – Manages item listings
- `IItemManager.java` – Item management interface

### 💬 Messaging System
- `Message.java` – Message structure
- `IMessage.java` – Message interface
- `MessageManager.java` – Handles messaging operations
- `IMessageManager.java` – Messaging system interface

### 💳 Payments
- `Payment.java` – Payment record structure
- `IPayment.java` – Interface for payments
- `PaymentManager.java` – Manages transactions and balances
- `IPaymentManager.java` – Payment manager interface
- `PaymentStatus.java` – Enum for transaction state

---

## 🧪 Testing Methodology

All core components are tested using **JUnit**, ensuring robustness and correctness. Test classes are located in the `test/` directory.

### ✅ Test Coverage Includes:
- `UserTest.java` – Account creation, login, balance updates
- `ItemTest.java` – Item addition and removal
- `MessageTest.java` – Sending and retrieving messages
- `PaymentTest.java` – Processing payments, balance updates, transaction status
- Simulated **concurrent access** to test thread safety in manager classes
