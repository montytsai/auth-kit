# Project AuthKit

A showcase project demonstrating a secure, deployable RESTful authentication API using modern Java practices.

This project is the result of a one-week personal sprint to build a production-ready, containerized, and CI/CD-enabled authentication service from scratch. It serves as a tangible proof of my ability to quickly learn, solve problems, and implement high-quality engineering practices.

[![Java CI with Maven](https://github.com/montytsai/auth-kit/actions/workflows/ci.yml/badge.svg)](https://github.com/montytsai/auth-kit/actions/workflows/ci.yml)

English | [繁體中文](README.zh-TW.md)

## ✨ Features

-   **User Registration & Login**: Secure RESTful endpoints for user registration and authentication using Spring Security. Passwords are encrypted with BCrypt.
-   **Robust Input Validation**: Implemented declarative, annotation-based validation for request DTOs using Jakarta Bean Validation.
-   **Unified Global Exception Handling**: A centralized `@RestControllerAdvice` provides consistent, clean JSON error responses for all API exceptions.
-   **Containerization Support**: Includes a multi-stage `Dockerfile` to build a lean, production-ready Docker image.
-   **Automated CI Pipeline**: A GitHub Actions workflow automatically builds and tests the project on every push to the `main` branch, ensuring code quality.

## 🛠️ Tech Stack

-   **Backend**: Java 17, Spring Boot 3 (Web, Security)
-   **Database**: In-Memory `ConcurrentHashMap` (for MVP)
-   **Build & Tooling**: Maven, Lombok, Jakarta Bean Validation
-   **DevOps**: Docker, GitHub Actions (CI)

## 🚀 Getting Started

### Prerequisites

-   Docker Desktop must be installed and running.

### Running with Docker (Recommended)

This is the simplest way to get the application running, as it doesn't require a local Java or Maven setup.

1.  **Clone the repository:**
    ```bash
    git clone [https://github.com/montytsai/auth-kit.git](https://github.com/montytsai/auth-kit.git)
    cd auth-kit
    ```

2.  **Build the Docker image:**
    ```bash
    docker build -t auth-kit .
    ```

3.  **Run the container:**
    ```bash
    docker run -p 8080:8080 auth-kit
    ```

4.  The application is now running and accessible at `http://localhost:8080`.

---

## 📝 API Usage

### 1. User Registration

-   **Endpoint**: `POST /api/auth/register`
-   **Description**: Creates a new user account.
-   **Request Body**:
    ```json
    {
        "email": "test@example.com",
        "password": "password123"
    }
    ```
-   **Success Response**:
    -   **Code**: `201 Created`
    -   **Body**: `"User registered successfully."`
-   **Error Responses**:
    -   **Code**: `400 Bad Request` (If validation fails, e.g., invalid email, short password).
    -   **Body**:
        ```json
        {
            "password": "Password must be at least 8 characters long",
            "email": "Invalid email format"
        }
        ```
    -   **Code**: `409 Conflict` (If the email already exists).
    -   **Body**:
        ```json
        {
            "error": "User with email test@example.com already exists."
        }
        ```

### 2. User Login

-   **Endpoint**: `POST /api/auth/login`
-   **Description**: Authenticates a user and returns a dummy token.
-   **Request Body**:
    ```json
    {
        "email": "test@example.com",
        "password": "password123"
    }
    ```
-   **Success Response**:
    -   **Code**: `200 OK`
    -   **Body**:
        ```json
        {
            "message": "Login successful!",
            "token": "dummy-jwt-token-for-test@example.com"
        }
        ```
-   **Error Responses**:
    -   **Code**: `401 Unauthorized` (If credentials are invalid).
    -   **Body**:
        ```json
        {
            "error": "Invalid credentials."
        }
        ```