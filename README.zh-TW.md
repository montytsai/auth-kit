# Project AuthKit

一個使用現代化 Java 實踐，旨在展示如何打造安全、可部署的 RESTful 認證 API 的作品集專案。

本專案是一個個人衝刺計畫的成果，目標是從零開始，建置一個具備生產就緒、容器化、並整合 CI/CD 的認證服務。它是我快速學習、解決問題，並將想法落地為高品質工程實踐能力的具體證明。

[![Java CI with Maven](https://github.com/montytsai/auth-kit/actions/workflows/ci.yml/badge.svg)](https://github.com/montytsai/auth-kit/actions/workflows/ci.yml)

[English](README.md) | 繁體中文

## ✨ 功能亮點 (Features)

-   **使用者註冊與登入**：提供 RESTful API 進行使用者註冊及身份驗證，密碼使用 BCrypt 進行安全雜湊。
-   **參數驗證**：透過 Jakarta Bean Validation 實現請求參數的 declarative 驗證，確保資料完整性。
-   **全域異常處理**：建立統一的 `GlobalExceptionHandler`，為 API 提供一致、清晰的錯誤回應格式。
-   **容器化支援**：提供一個採用多階段建置 (Multi-stage build) 的 `Dockerfile`，可產生輕量級、安全的正式環境映像檔。
-   **自動化 CI 流程**：整合 GitHub Actions，在每次推送到 `main` 分支時，自動執行編譯與測試，確保程式碼品質。

## 🛠️ 技術棧 (Tech Stack)

-   **後端**：Java 17、Spring Boot 3 (Web, Security)
-   **資料庫**：In-Memory `ConcurrentHashMap` (MVP 階段)
-   **建置與工具**：Maven、Lombok、Jakarta Bean Validation
-   **DevOps**：Docker、GitHub Actions (CI)

## 🚀 專案啟動指南 (Getting Started)

### 必要條件

-   Docker Desktop 已安裝並處於運行狀態。

### 使用 Docker 運行 (建議方式)

這是最簡單的運行方式，它不需要在本機安裝 Java 或 Maven 環境。

1.  **複製儲存庫**：
    ```bash
    git clone [https://github.com/montytsai/auth-kit.git](https://github.com/montytsai/auth-kit.git)
    cd auth-kit
    ```

2.  **建置 Docker 映像檔**：
    ```bash
    docker build -t auth-kit .
    ```

3.  **運行 Docker 容器**：
    ```bash
    docker run -p 8080:8080 auth-kit
    ```

4.  應用程式現在已成功運行，並可透過 `http://localhost:8080` 訪問。

---

## 📝 API 使用說明

### 1. 使用者註冊

-   **端點**：`POST /api/auth/register`
-   **說明**：建立一個新的使用者帳號。
-   **請求內文 (Request Body)**：
    ```json
    {
        "email": "test@example.com",
        "password": "password123"
    }
    ```
-   **成功回應**：
    -   **狀態碼**：`201 Created`
    -   **回應內文**：`"User registered successfully."`
-   **錯誤回應**：
    -   **狀態碼**：`400 Bad Request` (若驗證失敗)
    -   **回應內文**：
        ```json
        {
            "password": "Password must be at least 8 characters long",
            "email": "Invalid email format"
        }
        ```
    -   **狀態碼**：`409 Conflict` (若 Email 已存在)
    -   **回應內文**：
        ```json
        {
            "error": "User with email test@example.com already exists."
        }
        ```

### 2. 使用者登入

-   **端點**：`POST /api/auth/login`
-   **說明**：驗證使用者身份並回傳一個臨時 Token。
-   **請求內文 (Request Body)**：
    ```json
    {
        "email": "test@example.com",
        "password": "password123"
    }
    ```
-   **成功回應**：
    -   **狀態碼**：`200 OK`
    -   **回應內文**：
        ```json
        {
            "message": "Login successful!",
            "token": "dummy-jwt-token-for-test@example.com"
        }
        ```
-   **錯誤回應**：
    -   **狀態碼**：`401 Unauthorized` (若憑證無效)
    -   **回應內文**：
        ```json
        {
            "error": "Invalid credentials."
        }
        ```