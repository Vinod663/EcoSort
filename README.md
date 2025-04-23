# ♻️ ECOSORT Waste Management & Recycling Software (Layered Architecture)

> **Where Clean Code Meets Clean Communities**

This branch reflects the **Layered Architecture** implementation of ECOSORT — a software tool designed for managing municipal waste collection, segregation, and recycling.

---

## 🚀 Key Features

### 🗑️ Waste Collection Management
- Classify waste into degradable, recyclable, and non-recyclable types.
- Maintain records per municipal division/ward.

### 📅 Scheduling Engine
- Define custom pickup schedules for all zones with time and vehicle assignments.

### 🚛 Fleet & Route Management
- Register and assign vehicles to routes and track maintenance.

### 👷 Workforce Management
- Track employee assignments and responsibilities.

### 📦 Inventory Tracking
- Check current storage levels and recycling capacities.

### 📊 Reporting System
- Insightful charts and downloadable reports using **JasperReports**.

---

## 🧰 Tech Stack

- **Java 21**
- **JavaFX** for front-end
- **MySQL** as database
- **JDBC** for Communicate with MySQL
- **JasperReports**, **Maven**, **Lombok**
- **Layered Architecture** (UI, Service, Repository, Domain)

---

## 📁 Project Structure
```
src/
├── controller/
├── BO/
├── dao/
├── db/
├── dto/
├── entity/
├── view/       # FXML files
├── reports/    # JRXML & compiled reports
├── assets/     # Logos, icons
└── main/SerenityApp.java
```

---

## 🧾 License

This project is licensed under the [MIT License](LICENSE).

---

## 👨‍💻 Developed By

**Vinod Niloshana Fernando**  
*Student | Software Engineer*  
[🔗 LinkedIn](https://www.linkedin.com/in/vinod-niloshana-09678731a/) • [💻 GitHub](https://github.com/Vinod663)

**[branch-master: Layered]**
**[branch-main: MVC]**

