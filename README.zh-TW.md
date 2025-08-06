# 📦 AuthKit 專案介紹

[![Java CI with Maven](https://github.com/montytsai/auth-kit/actions/workflows/ci.yml/badge.svg)](https://github.com/montytsai/auth-kit/actions/workflows/ci.yml)
[![Deploy to Render](https://img.shields.io/badge/deploy-render-blue?logo=render)](https://auth-kit-montytsai.onrender.com)

一個使用現代 Java 技術打造的 **安全、可部署的 RESTful 認證 API** 展示專案。

本專案是我一週衝刺的成果，目標是從零開始打造一個具備 **生產等級、容器化、CI/CD 支援** 的認證服務。  
它是我具體展現快速學習、解決實務問題、並實踐高品質工程能力的證明。

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

- **安全認證機制**：整合 Spring Security，密碼使用 BCrypt 進行安全雜湊儲存。
- **宣告式輸入驗證**：採用 Jakarta Bean Validation，確保 API 輸入資料的正確性。
- **全域例外處理**：透過 `@RestControllerAdvice` 建立集中式錯誤處理，提供一致的回應格式。
- **容器化部署**：編寫多階段 `Dockerfile`，建置輕量化、適合生產環境的 Docker 映像。
- **整合測試覆蓋**：使用 `SpringBootTest` 與 `MockMvc` 撰寫完整的 API 整合測試，確保端點行為穩定且符合預期。
- **CI/CD 自動化**：設定 GitHub Actions，實現從程式碼提交到線上部署的全自動化流程。

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

## 🔁 CI/CD 自動化流程

本專案已建立完整的 CI/CD（持續整合／持續部署）流程，從程式碼提交到服務上線皆為全自動化，確保了高品質的交付效率。

`Push to main` → `GitHub Actions (CI)` → `Render (CD)`

1.  **推送程式碼 (Push)**：開發者將變更推送至 `main` 分支。

2.  **持續整合 (CI) on GitHub Actions**：推送後自動觸發 GitHub Actions，執行一系列品質檢驗：
    -   ⚙️ **建置與測試 (Build & Test)**：使用 Maven 編譯專案，並執行所有使用 `SpringBootTest` 撰寫的整合測試，驗證核心功能。
    -   🐞 **靜態分析 (Static Analysis)**：整合 `SpotBugs` 工具掃描程式碼，在編譯階段早期發現潛在的 Bug 與程式碼壞味道 (bad smells)。

3.  **持續部署 (CD) to Render**：一旦 CI 流程中的所有檢查點皆成功通過，workflow 會自動執行下一步：
    -   🚀 **觸發部署 (Trigger Deployment)**：透過安全的 Deploy Hook，通知 **Render** 平台拉取最新的程式碼映像並完成線上部署。

此自動化流程不僅確保了只有通過完整測試與分析的穩定版本才能上線，也讓線上展示永遠保持在最新狀態。

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

啟動後，服務位於：`http://localhost:8080`  
Swagger UI：`http://localhost:8080/swagger-ui/index.html`

---

## 📝 API 文件

所有 API 規格、請求與回應範例，請參考上方的 [Swagger UI](#-互動式-API-文件-Swagger-UI)。

