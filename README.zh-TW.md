# 📦 AuthKit 專案介紹

一個使用現代 Java 技術打造的 **安全、可部署的 RESTful 認證 API** 展示專案。

本專案是我一週衝刺的成果，目標是從零開始打造一個具備 **生產等級、容器化、CI/CD 支援** 的認證服務。  
它是我具體展現快速學習、解決實務問題、並實踐高品質工程能力的證明。

[![Java CI with Maven](https://github.com/montytsai/auth-kit/actions/workflows/ci.yml/badge.svg)](https://github.com/montytsai/auth-kit/actions/workflows/ci.yml)

[English](README.md) | 繁體中文

---

## 🌐 線上展示

服務部署於 Render，公開可用：

> **基底 URL**：[`https://auth-kit-montytsai.onrender.com`](https://auth-kit-montytsai.onrender.com)

---

## 📄 互動式 API 文件 Swagger UI

專案內建 Swagger UI，可直接於瀏覽器查看與測試 API：

> **[前往 Swagger UI](https://auth-kit-montytsai.onrender.com/swagger-ui/index.html)**

---

## ✨ 功能亮點

- **使用者註冊與登入**：整合 Spring Security，密碼使用 BCrypt 雜湊。
- **輸入驗證**：使用 Jakarta Bean Validation 進行宣告式驗證。
- **全域異常處理**：集中式錯誤處理機制，統一回應格式。
- **容器化支援**：使用多階段建置，產出輕量 Docker 映像。
- **CI 自動化**：GitHub Actions 自動編譯與測試每次提交。

---

## 🛠️ 技術棧

- **後端**：Java 17、Spring Boot 3（Web、Security）
- **資料庫**：記憶體型 ConcurrentHashMap（MVP 階段）
- **API 文件**：SpringDoc OpenAPI（Swagger UI）
- **建置工具**：Maven、Lombok、Jakarta Bean Validation
- **DevOps**：Docker、GitHub Actions

---

## 🏛️ 系統架構

遵循標準分層架構，強調可讀性與維護性。

```
├── .github/workflows/         # GitHub Actions CI 設定
├── src/main/java
│   └── io/github/montytsai/authkit
│       ├── config/            # Security 設定、Bean 配置
│       ├── controller/        # API 端點
│       ├── dto/               # 請求／回應資料結構
│       ├── exception/         # 全域錯誤處理與自定義異常
│       ├── service/           # 核心商業邏輯
│       └── AuthKitApplication.java
├── src/main/resources/        # 設定檔
├── Dockerfile                 # 多階段建置 Dockerfile
└── pom.xml                    # Maven 專案設定
```

---

## 🚀 本地執行方式

### 前置需求

- 已安裝並啟動 Docker Desktop

### 使用 Docker 運行（推薦）

```bash
# 複製儲存庫
git clone https://github.com/montytsai/auth-kit.git
cd auth-kit

# 建置映像
docker build -t auth-kit .

# 運行容器
docker run -p 8080:8080 auth-kit
```

啟動後，服務位於：  
🔗 `http://localhost:8080`  
Swagger UI：`http://localhost:8080/swagger-ui/index.html`

---

## 📝 API 文件

所有 API 規格、請求與回應範例，請參考上方的 [Swagger UI](#-互動式-API-文件-Swagger-UI)。

