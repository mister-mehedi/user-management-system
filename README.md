# User Management System

A simple RESTful API built with Spring Boot that demonstrates fundamental concepts including database integration with PostgreSQL and role-based security using Spring Security.

---

## 📋 Table of Contents

- [Project Overview](#project-overview)
- [🚀 Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [🗄️ Database Setup](#️database-setup)
    - [🔧 Application Configuration](#application-configuration)
- [▶️ Running the Application](#️running-the-application)

---

## 📝 Project Overview

The primary goal of this project is to provide a secure backend service for managing users. It includes basic authentication and role-based access control for different API endpoints.

### Core Technologies

- Java 17
- Spring Boot 3.x
- Spring Security
- Spring Data JPA
- PostgreSQL
- Maven

---

## 🚀 Getting Started

Follow these instructions to get a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

Make sure you have the following software installed on your system:

- [Java Development Kit (JDK) 17 or higher](https://www.oracle.com/java/technologies/javase-jdk17-downloads.html)
- [Apache Maven](https://maven.apache.org/download.cgi)
- [PostgreSQL](https://www.postgresql.org/download/)
- An IDE of your choice (e.g., IntelliJ IDEA, VS Code, Eclipse)

---

## 🗄️ Database Setup

You need to create a PostgreSQL database for the application to connect to.

1. Open your PostgreSQL client (like `psql` or a GUI tool like pgAdmin).
2. Create a new database named `intern_db`:

   ```sql
   CREATE DATABASE intern_db;
   ```

3. Create a dedicated user and grant privileges:

   ```sql
   CREATE USER myuser WITH PASSWORD 'mypassword';
   GRANT ALL PRIVILEGES ON DATABASE intern_db TO myuser;
   ```

---

## 🔧 Application Configuration

1. Clone the repository to your local machine:

   ```bash
   git clone <your-repository-url>
   cd user-management-system
   ```

2. Navigate to the `src/main/resources` directory and open the `application.properties` file.

3. Update the database connection properties:

   ```properties
   # PostgreSQL Database connection settings
   spring.datasource.url=jdbc:postgresql://localhost:5432/intern_db
   spring.datasource.username=myuser
   spring.datasource.password=mypassword
   spring.datasource.driver-class-name=org.postgresql.Driver
   ```

---

## ▶️ Running the Application

Once the prerequisites and configuration are complete, you can run the application using Maven:

1. Open a terminal or command prompt in the root directory of the project.
2. Execute the following Maven command:

   ```bash
   mvn spring-boot:run
   ```

The application will start on the embedded Tomcat server, typically on port `8080`. You should see log output indicating a successful connection to the `intern_db` database.
