# 🖥️ CS18000 Marketplace Project — Phase 1

---

## 📌 Overview

This phase implements the backend of a multithreaded Marketplace system in Java, allowing users to register, manage items, send messages, and make payments. The design supports multiple clients via a single server and ensures thread-safe access through atomic counters and synchronized methods.

---

## ✅ Core Functionality

### 1. 👤 User Accounts
- Users generate a **unique userID** through `AtomicInteger`
- They keep and update their **account balances**
- They register with a **unique username and password**
- Both buyers and sellers are supported via the `User` class

### 2. 📦 Item Listings
- The following are included in each `Item`: `sellerID`, `name`, `price`, and `pictureFilename`
- Every object is assigned a **unique itemID**
- Through `ItemManager`, users can **add/remove** things from their listings

### 3. 💬 Messaging System
- Buyers can use `MessageManager` to message sellers
- Each `Message` has `text`, `senderID`, and `receiverID`
- Thread-safe **storage and retrieval** of messages

### 4. 💳 Payment Processing
- The following payment records are stored: `buyerID`, `sellerID`, `amount`, `timestamp`, `status`
- `PaymentManager` manages **transaction status** and **balance updates**
- The `PaymentStatus` enum specifies `PENDING`, `COMPLETED`, and `FAILED`

---

## 🧵 Thread Safety & Concurrency

- The following unique IDs are generated by `AtomicInteger`: `userID`, `itemID`, and `paymentID`
- Every manager class, including `UserManager`, `ItemManager`, and others, uses **synchronized methods**
- A centralized server approach allows for **multi-client concurrency**

---

## 📁 Files Included

### 👥 User Management
- `User.java` – Definition of the user object and fundamental logic  
- `UserManager.java` – User management logic  
- `IUser.java` – User interface specification  
- `IUserManager.java` – User management interface  

### 📦 Managing Items
- `Item.java` – Item object and fields  
- `IItem.java` – Item interface  
- `ItemManager.java` – Item listing management  
- `IItemManager.java` – Item management interface  

### 💬 Messaging System
- `Message.java` – Message structure  
- `IMessage.java` – Message interface  
- `MessageManager.java` – Manages messaging operations  
- `IMessageManager.java` – Messaging system interface  

### 💳 Payments
- `Payment.java` – Structure of payment records  
- `IPayment.java` – Payment interface  
- `PaymentManager.java` – Transaction and balance management  
- `IPaymentManager.java` – Payment manager interface  
- `PaymentStatus.java` – Transaction state enum  

---

## 🧪 Testing Methodology

All essential components are tested using **JUnit**, guaranteeing correctness and robustness. The `test/` directory contains the test classes.

### ✅ Test Coverage Includes:
- `UserTest.java` – Account creation, login, and balance updates  
- `UserManagerTest.java` – User retrieval and validation logic  
- `ItemTest.java` – Adding and removing items  
- `ItemManagerTest.java` – Item management operations  
- `MessageTest.java` – Sending and receiving messages  
- `MessageManagerTest.java` – Message tracking and retrieval  
- `PaymentTest.java` – Simulated **concurrent access** while processing payments, balance updates, and transaction status  
- `PaymentManagerTest.java` – Payment processing and transaction updates
