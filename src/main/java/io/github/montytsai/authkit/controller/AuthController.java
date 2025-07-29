package io.github.montytsai.authkit.controller;

import io.github.montytsai.authkit.dto.RegisterRequest;
import io.github.montytsai.authkit.service.AuthService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        if (registerRequest == null) {
            log.warn("Received registration request with null body.");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request body cannot be empty.");
        }

        try {
            // 呼叫 service 層執行註冊邏輯
            authService.register(registerRequest);
            // 成功時回傳 201 Created 狀態碼和成功訊息
            return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
        } catch (IllegalStateException e) {
            // 如果 Service 拋出使用者已存在的異常，回傳 409 Conflict
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

}