# User Management System

A simple RESTful API built with Spring Boot that demonstrates fundamental concepts including database integration with PostgreSQL and role-based security using Spring Security.

---

## 📋 Table of Contents

- [📝 Project Overview](#project-overview)
- [🚀 Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [🗄️ Database Setup](#️database-setup)
    - [🔧 Application Configuration](#application-configuration)
- [▶️ Running the Application](#️running-the-application)
- [💾 Data Model](#data-model)
- [🛡️ Security Configuration](#security-configuration)
- [🔌 API Endpoints](#api-endpoints)
- [🧪 Testing with Postman](#testing-with-postman)
- [🤔 Assumptions](#assumptions)

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

Follow these instructions to get a copy of the project up and running on your local machine.

### Prerequisites

- JDK 17 or higher
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

2. Open the `src/main/resources/application.properties` file and update:

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

## 💾 Data Model

The application's data layer is managed by **Spring Data JPA**.

- **User Entity**: Maps to the `users` table and defines the user structure (`id`, `username`, `password`, `role`).
- **UserRepository**: Extends `JpaRepository` to provide full CRUD functionality for `User` entities.

---

## 🛡️ Security Configuration

Security is configured in the `SecurityConfig` class.

- **Authentication**: HTTP Basic Authentication is used. Two in-memory users are created:
    - `intern` (Role: USER)
    - `admin` (Role: ADMIN)

- **Password Encoding**: `BCryptPasswordEncoder` is used to securely hash and store all passwords.

- **Authorization**: Endpoint access is restricted based on roles:
    - `/public`: Open to all.
    - `/user`: Requires `USER` or `ADMIN` role.
    - `/admin`, `/users`: Requires `ADMIN` role.

---

## 🔌 API Endpoints

The `UserController` class exposes the following REST endpoints:

| Method | Endpoint   | Description                    | Access        |
|--------|------------|--------------------------------|---------------|
| GET    | `/public`  | A public endpoint for anyone   | Public        |
| GET    | `/user`    | An endpoint for users          | USER, ADMIN   |
| GET    | `/admin`   | An endpoint for admins only    | ADMIN         |
| POST   | `/users`   | Creates a new user             | ADMIN         |

#### Request Body Example (POST `/users`)

```json
{
  "username": "newuser",
  "password": "newpassword123",
  "role": "USER"
}
```

---

## 🧪 Testing with Postman

You can use a Postman collection or test manually.

- For secured endpoints, use **Basic Auth** with the following credentials:
    - `intern` / `password123`
    - `admin` / `admin123`

---

## 🤔 Assumptions

- The application uses an **in-memory user store** for two predefined users (`intern`, `admin`) for simplicity and as per assignment requirements.
- Users created via the `POST /users` endpoint are persisted in the **PostgreSQL** database.
- **CSRF protection is disabled**, which is common for stateless REST APIs.

