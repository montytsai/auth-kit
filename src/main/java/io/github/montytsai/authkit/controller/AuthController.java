package io.github.montytsai.authkit.controller;

import io.github.montytsai.authkit.dto.RegisterRequest;
import io.github.montytsai.authkit.service.AuthService;
import jakarta.validation.Valid;
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
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest registerRequest) {
        String email = registerRequest.getEmail();
        log.info("Received registration request for user: {}", email);

        // 呼叫 service 層執行註冊邏輯
        authService.register(registerRequest);

        log.info("User {} registered successfully.", email);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully."); //201
    }

}