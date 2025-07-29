# Project AuthKit

A showcase project demonstrating a secure RESTful authentication API using modern Java practices.

This project is part of a personal "sprint" to build a production-ready, containerized, and CI/CD-enabled authentication service. It serves as a tangible proof of my ability to quickly learn, solve problems, and implement high-quality engineering practices.

## ✨ Features (MVP)

- **User Registration**: `POST /api/auth/register` with email and password. Passwords are securely hashed using BCrypt.
- **User Login**: `POST /api/auth/login` to authenticate users.
- **(Upcoming)** Magic Number Authentication.

## 🛠️ Tech Stack

- **Backend**: Java 17, Spring Boot 3
- **Core Dependencies**: Spring Web, Spring Security, Lombok
- **Database**: H2 (for local development), PostgreSQL (for production)
- **Build Tool**: Maven
- **DevOps**: Docker, GitHub Actions (CI/CD)

## 🚀 Getting Started

### Prerequisites

- Java 17+
- Maven 3.8+

### Running Locally

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/montytsai/auth-kit.git](https://github.com/montytsai/auth-kit.git)
    cd auth-kit
    ```

2.  **Run the application using Maven Wrapper:**
    ```bash
    ./mvnw spring-boot:run
    ```

3.  The application will be running at `http://localhost:8080`.

## 📝 API Endpoints

(Details will be added as features are completed.)

---
*This README will be continuously updated throughout the project's development.*