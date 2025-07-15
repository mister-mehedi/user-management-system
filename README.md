# User Management System

A simple RESTful API built with Spring Boot that demonstrates fundamental concepts including database integration with PostgreSQL, role-based security, and advanced features like custom error handling and unit testing.

---

## 📋 Table of Contents

- [📝 Project Overview](#project-overview)
- [💻 Core Technologies](#core-technologies)
- [🚀 Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [🗄️ Database Setup](#️database-setup)
    - [🔧 Application Configuration](#application-configuration)
- [▶️ Running the Application](#️running-the-application)
- [🔌 API Endpoints](#api-endpoints)
- [✨ Advanced Features](#advanced-features-bonus)
    - [Input Validation](#input-validation)
    - [Global Error Handling](#global-error-handling)
    - [Unit & Integration Testing](#unit--integration-testing)
- [🤔 Assumptions](#assumptions)

---

## 📝 Project Overview

The primary goal of this project is to provide a secure backend service for managing users. It includes basic authentication and role-based access control for different API endpoints, as well as robust error handling and a suite of integration tests.

---

## 💻 Core Technologies

- Java 24
- Spring Boot 3.5.3
- Spring Security
- Spring Data JPA
- PostgreSQL
- Maven

---

## 🚀 Getting Started

Follow these instructions to get a copy of the project up and running on your local machine.

### Prerequisites

- JDK 24 or higher
- Apache Maven
- PostgreSQL

---

## 🗄️ Database Setup

1. Create a PostgreSQL database named `intern_db`:

   ```sql
   CREATE DATABASE intern_db;
   ```

2. Create a user and grant privileges (replace `myuser` and `mypassword` with your credentials):

   ```sql
   CREATE USER myuser WITH PASSWORD 'mypassword';
   GRANT ALL PRIVILEGES ON DATABASE intern_db TO myuser;
   ```

---

## 🔧 Application Configuration

1. Clone the repository:

   ```bash
   git clone <your-repository-url>
   cd user-management-system
   ```

2. In `src/main/resources/application.properties`, update:

   ```properties
   spring.datasource.username=myuser
   spring.datasource.password=mypassword
   ```

---

## ▶️ Running the Application

Use the following Maven command to run the application:

```bash
mvn spring-boot:run
```

The application will start on [http://localhost:8080](http://localhost:8080).

---

## 🔌 API Endpoints

The `UserController` class exposes the following REST endpoints:

| Method | Endpoint   | Description                    | Access        |
|--------|------------|--------------------------------|---------------|
| GET    | `/public`  | A public endpoint for anyone   | Public        |
| GET    | `/user`    | An endpoint for users          | USER, ADMIN   |
| GET    | `/admin`   | An endpoint for admins only    | ADMIN         |
| POST   | `/users`   | Creates a new user             | ADMIN         |

### Request Body Example (POST `/users`)

```json
{
  "username": "newuser",
  "password": "newpassword123",
  "role": "USER"
}
```

---

## ✨ Advanced Features

This project implements several advanced features to ensure code quality and robustness.

### Input Validation

The `POST /users` endpoint uses the Spring Validation framework. Fields like `username`, and `password` are automatically validated for constraints such as length and format.  
Invalid requests receive a `400 Bad Request` response with a list of clear errors.

---

### Global Error Handling

A centralized `GlobalExceptionHandler` is implemented to provide consistent and user-friendly JSON error responses for common issues:

- **400 Bad Request**: For validation failures.
- **403 Forbidden**: For authorization failures (e.g., a USER trying to access an ADMIN resource). This is handled by a custom `AccessDeniedHandler`.
- **409 Conflict**: For attempts to create a user with a username or email that already exists.

---

### Unit & Integration Testing

The project includes a comprehensive suite of integration tests for the `UserController` in `src/test`.

- **Strategy**: Uses `@SpringBootTest` to load the full application context.
- **Tools**:
    - `MockMvc` to simulate HTTP requests
    - `@MockBean` to mock the `UserRepository` and isolate tests from the database
- **Coverage**:
    - Successful endpoint access
    - Security failures (unauthenticated and unauthorized access)
    - User creation logic
- **To Run Tests**:

  ```bash
  mvn test
  ```

---

## 🤔 Assumptions

- The application uses an **in-memory user store** for two predefined users (`intern`, `admin`) for simplicity.
- Users created via the `POST /users` endpoint are **persisted** in the PostgreSQL database.
- **CSRF protection is disabled**, which is a common practice for stateless REST APIs.
