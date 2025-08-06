# 📦 Project AuthKit

[![Java CI with Maven](https://github.com/montytsai/auth-kit/actions/workflows/ci.yml/badge.svg)](https://github.com/montytsai/auth-kit/actions/workflows/ci.yml)
[![Deploy to Render](https://img.shields.io/badge/deploy-render-blue?logo=render)](https://auth-kit-montytsai.onrender.com)

A showcase project demonstrating a **secure, deployable RESTful authentication API** built with modern Java practices.

This project is the result of a **one-week personal sprint**, aimed at building a **production-ready, containerized, CI/CD-enabled** authentication service from scratch.  
It serves as a concrete proof of my ability to **quickly learn, solve real-world problems, and apply high-quality engineering practices**.

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

- **Secure Authentication**: Integrated with Spring Security, with passwords securely hashed using BCrypt.
- **Declarative Validation**: Robust input validation using Jakarta Bean Validation annotations.
- **Global Exception Handling**: Centralized error handling via `@RestControllerAdvice` for consistent API responses.
- **Containerized**: Production-ready, lightweight Docker images built using a multi-stage `Dockerfile`.
- **Integration Testing**: Comprehensive API integration tests written with `SpringBootTest` and `MockMvc` to ensure endpoint stability and correctness.
- **CI/CD Pipeline**: Fully automated workflow from push-to-main to production deployment using GitHub Actions.

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

## 🔁 CI/CD Automation Workflow

This project implements a complete CI/CD (Continuous Integration / Continuous Deployment) pipeline for fully automated, high-quality delivery from code commit to production.

`Push to main` → `GitHub Actions (CI)` → `Render (CD)`

1.  **Code Push**: A developer pushes new commits to the `main` branch.

2.  **Continuous Integration (CI) on GitHub Actions**: The push automatically triggers a GitHub Actions workflow that performs several quality checks:
    -   ⚙️ **Build & Test**: Compiles the project with Maven and runs a full suite of integration tests written with `SpringBootTest` to validate core functionalities.
    -   🐞 **Static Analysis**: Integrates the `SpotBugs` tool to scan the codebase for potential bugs and code smells early in the pipeline.

3.  **Continuous Deployment (CD) to Render**: Upon the successful completion of all CI checks, the workflow proceeds to the final step:
    -   🚀 **Trigger Deployment**: It securely calls a **Render** Deploy Hook, instructing the platform to pull the latest build and deploy it to the live environment.

This automated pipeline ensures that only stable, fully-tested code is deployed to production, keeping the live demo service consistently up-to-date.

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

App will be accessible at: `http://localhost:8080`  
Swagger UI: `http://localhost:8080/swagger-ui/index.html`

---

## 📝 API Reference

See [Swagger UI](#-interactive-api-documentation-swagger-ui) for complete API specifications, schemas, and usage.
