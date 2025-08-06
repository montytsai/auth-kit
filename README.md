# 📦 Project AuthKit

A showcase project demonstrating a **secure, deployable RESTful authentication API** built with modern Java practices.

This project is the result of a **one-week personal sprint**, aimed at building a **production-ready, containerized, CI/CD-enabled** authentication service from scratch.  
It serves as a concrete proof of my ability to **quickly learn, solve real-world problems, and apply high-quality engineering practices**.

[![Java CI with Maven](https://github.com/montytsai/auth-kit/actions/workflows/ci.yml/badge.svg)](https://github.com/montytsai/auth-kit/actions/workflows/ci.yml)

English | [繁體中文](README.zh-TW.md)

---

## 🌐 Live Demo

Deployed on Render and publicly accessible:

> **Base URL**: [`https://auth-kit-montytsai.onrender.com`](https://auth-kit-montytsai.onrender.com)

---

## 📄 Interactive API Documentation (Swagger UI)

This project includes an auto-generated Swagger UI for API exploration and testing:

> **[View Live Swagger UI](https://auth-kit-montytsai.onrender.com/swagger-ui/index.html)**

---

## ✨ Features

- **User Registration & Login**: Secure RESTful endpoints with Spring Security; passwords hashed with BCrypt.
- **Robust Input Validation**: Declarative validation using Jakarta Bean Validation.
- **Global Exception Handling**: Centralized `@RestControllerAdvice` provides consistent error responses.
- **Containerization**: Lightweight production-ready images using a multi-stage `Dockerfile`.
- **CI Pipeline**: GitHub Actions runs build & test workflows on every push.

---

## 🛠️ Tech Stack

- **Backend**: Java 17, Spring Boot 3 (Web, Security)
- **Database**: In-memory `ConcurrentHashMap` (for MVP)
- **Docs**: SpringDoc OpenAPI (Swagger UI)
- **Build Tools**: Maven, Lombok, Jakarta Bean Validation
- **DevOps**: Docker, GitHub Actions

---

## 🏛️ System Architecture

Follows a standard layered architecture for separation of concerns and maintainability.

```
├── .github/workflows/         # CI pipeline (GitHub Actions)
├── src/main/java
│   └── io/github/montytsai/authkit
│       ├── config/            # Bean configurations (Security, Encoder, etc.)
│       ├── controller/        # REST endpoints
│       ├── dto/               # Request/Response DTOs
│       ├── exception/         # Global exception handlers & custom errors
│       ├── service/           # Business logic layer
│       └── AuthKitApplication.java
├── src/main/resources/        # Config files (e.g., application.properties)
├── Dockerfile                 # Multi-stage Docker build
└── pom.xml                    # Maven config
```

---

## 🚀 Getting Started (Local)

### Prerequisites

- Docker Desktop installed and running

### Run with Docker (Recommended)

```bash
# Clone the repository
git clone https://github.com/montytsai/auth-kit.git
cd auth-kit

# Build the Docker image
docker build -t auth-kit .

# Run the container
docker run -p 8080:8080 auth-kit
```

App will be accessible at:  
🔗 `http://localhost:8080`  
Swagger UI: `http://localhost:8080/swagger-ui/index.html`

---

## 📝 API Reference

See [Swagger UI](#-interactive-api-documentation-swagger-ui) for complete API specifications, schemas, and usage.
