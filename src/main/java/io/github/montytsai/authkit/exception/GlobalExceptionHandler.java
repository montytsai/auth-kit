package io.github.montytsai.authkit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 參數驗證錯誤: 400
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            // 取得錯誤的欄位名稱
            String fieldName = ((FieldError) error).getField();
            // 取得在 DTO 中定義的錯誤訊息
            String errorMessage = error.getDefaultMessage();
            // 將欄位名稱和錯誤訊息放入 Map
            errors.put(fieldName, errorMessage);
        });

        // 回傳 HTTP 400 狀態碼，及整理好的、乾淨的錯誤訊息 Map
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    /**
     * 請求 Body 缺失或格式錯誤: 400
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", "Request body is missing or malformed.");
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    /**
     * 請求方法不被支援: 405
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Map<String, String>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        Map<String, String> error = new HashMap<>();
        String message = "Request method '" + ex.getMethod() + "' is not supported. Supported methods are " + ex.getSupportedHttpMethods();
        error.put("error", message);
        return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
    }

    /**
     * 處理「使用者已存在」的業務異常: 409
     */
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Map<String, String>> handleUserAlreadyExistsException(UserAlreadyExistsException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    /**
     * 處理所有其他未被捕獲的未知異常: 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleUncaughtException(Exception ex) {
        log.error("An unexpected error occurred: {}", ex.getMessage(), ex);

        // 回傳給客戶端的，則是一個通用的、模糊的錯誤訊息，避免洩漏系統內部細節
        Map<String, String> error = new HashMap<>();
        error.put("error", "An unexpected internal server error occurred.");

        // 對於所有未知的伺服器內部錯誤，回傳 500 Internal Server Error 是標準作法
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}