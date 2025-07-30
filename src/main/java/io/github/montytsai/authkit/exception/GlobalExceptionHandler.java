package io.github.montytsai.authkit.exception;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

/**
 * **全域異常處理器 (Global Exception Handler)。**
 * 統一處理應用程式中控制器層拋出的特定異常，並轉換為標準化的 HTTP 錯誤響應。
 * 這有助於將錯誤處理與業務邏輯分離，保持控制器層的清潔。
 *
 * @apiNote 所有異常響應都應提供一致的結構（例如，包含 HTTP 狀態碼、錯誤訊息和詳細資訊），
 * 以便前端能可靠地解析和顯示。
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * **處理參數驗證錯誤 (HTTP 400 Bad Request)。**
     * 當請求數據不符合 DTO 中定義的 {@code @Valid} 驗證規則時觸發。
     *
     * @param ex {@link MethodArgumentNotValidException} 實例。
     * @return 包含所有字段錯誤訊息的 {@link ResponseEntity}。
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        log.warn("Request validation failed: {}", errors);
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * **處理請求 Body 缺失或格式錯誤 (HTTP 400 Bad Request)。**
     * 當 Spring 無法將 HTTP 請求的 Body 解析為控制器方法參數時觸發。
     *
     * @param ex {@link HttpMessageNotReadableException} 實例。
     * @return 包含通用錯誤訊息的 {@link ResponseEntity}。
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Request body is missing or malformed. Please check your JSON format.");
        log.warn("HTTP message not readable: {}", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * **處理身份驗證失敗 (HTTP 401 Unauthorized)。**
     * 當使用者提供的憑證不正確，導致 Spring Security 無法完成身份驗證時觸發。
     *
     * @param ex {@link AuthenticationException} 實例。
     * @return 包含通用認證失敗訊息的 {@link ResponseEntity}。
     * @apiNote 出於安全考慮，不建議回傳過於詳細的錯誤訊息（如「密碼錯誤」），以避免被用於枚舉用戶名或暴力破解。
     */
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, String>> handleAuthenticationException(AuthenticationException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Invalid credentials. Please check your email and password.");
        log.warn("Authentication failed: {}", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    /**
     * **處理請求方法不支援 (HTTP 405 Method Not Allowed)。**
     * 當客戶端使用不被支援的 HTTP 方法訪問資源時觸發。
     *
     * @param ex {@link HttpRequestMethodNotSupportedException} 實例。
     * @return 包含說明支援方法的 {@link ResponseEntity}。
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, String>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        Map<String, String> error = new HashMap<>();

        String supportedMethods = Optional.ofNullable(ex.getSupportedHttpMethods()).orElse(Collections.emptySet())
                .stream().map(HttpMethod::name)
                         .collect(Collectors.joining(", "));

        if (supportedMethods.isEmpty()) {
            supportedMethods = "N/A";
        }

        String message = String.format("Request method '%s' is not supported for this endpoint. Supported methods are: %s",
                ex.getMethod(), supportedMethods);
        error.put("error", message);
        log.warn("Method Not Allowed: {}. Supported methods: {}", ex.getMethod(), supportedMethods);
        return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * **處理「使用者已存在」業務異常 (HTTP 409 Conflict)。**
     * 當業務邏輯檢測到資源衝突（如嘗試創建已存在的用戶）時拋出。
     *
     * @param ex {@link UserAlreadyExistsException} 實例。
     * @return 包含業務錯誤訊息的 {@link ResponseEntity}，狀態碼為 409。
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        log.warn("Business conflict: {}", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    /**
     * **處理所有其他未被捕獲的未知異常 (HTTP 500 Internal Server Error)。**
     * 作為最終的 fallback 處理器，捕獲所有未預期的系統內部錯誤。
     *
     * @param ex {@link Exception} 實例。
     * @param request {@link WebRequest} 物件，用於獲取請求資訊。
     * @return 包含通用錯誤訊息的 {@link ResponseEntity}，狀態碼為 500。
     * @apiNote 對於未知內部錯誤，僅向客戶端返回通用訊息，避免洩露系統細節，保障生產環境安全。
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleUncaughtException(Exception ex, WebRequest request) {
        log.error("An unexpected internal server error occurred for URI: {}. Error: {}",
                request.getDescription(false), ex.getMessage(), ex);

        Map<String, String> error = new HashMap<>();
        error.put("error", "An unexpected internal server error occurred. Please try again later.");

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}