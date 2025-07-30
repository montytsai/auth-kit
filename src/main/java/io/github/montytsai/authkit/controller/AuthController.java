package io.github.montytsai.authkit.controller;

import io.github.montytsai.authkit.dto.LoginRequest;
import io.github.montytsai.authkit.dto.LoginResponse;
import io.github.montytsai.authkit.dto.RegisterRequest;
import io.github.montytsai.authkit.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 認證相關的 RESTful API 控制器。
 * 處理使用者註冊、登入等身份驗證流程。
 *
 * @apiNote 所有 response 都應包含明確的 HTTP 狀態碼，
 * 異常情況由 {@link io.github.montytsai.authkit.exception.GlobalExceptionHandler} 統一處理。
 */
@RestController
@RequestMapping("/api/auth")
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthService authService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    /**
     * 處理新使用者的註冊請求。
     *
     * @param registerRequest 包含使用者電子郵件和密碼的 DTO。
     * @return 註冊成功時回傳成功代碼。
     * @apiNote 業務層將檢查 Email 唯一性，若衝突拋出 {@link io.github.montytsai.authkit.exception.UserAlreadyExistsException}。
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest registerRequest) {
        String email = registerRequest.getEmail();
        log.info("Received registration request for user: {}", email);

        authService.register(registerRequest);

        log.info("User {} registered successfully.", email);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully.");
    }

    /**
     * 處理使用者的登入請求。
     *
     * @param loginRequest 包含使用者電子郵件和密碼的 DTO。使用 {@code @Valid} 進行輸入格式驗證。
     * @return {@link LoginResponse} 登入成功時回傳成功資訊，包含 JWT Token
     * @apiNote 身份驗證失敗時，{@link AuthenticationManager} 會拋出異常。（例如 BadCredentialsException）
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        log.info("Attempting login for user: {}", email);

        // 1. 將使用者參數整理為 Spring Security 的 Authentication 物件
        Authentication authenticationRequest = new UsernamePasswordAuthenticationToken(email, loginRequest.getPassword());

        // 2. 交由 AuthenticationManager 進行認證
        Authentication authenticationResponse = authenticationManager.authenticate(authenticationRequest);

        // 3. 認證成功，將認證資訊存入 SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authenticationResponse);

        // TODO 4. (未來實現) 生成 JWT Token
        String token = "dummy-jwt-token-for-" + email;

        // 5. 回傳成功響應
        log.info("User {} logged in successfully.", email);
        return ResponseEntity.ok(new LoginResponse("Login successful!", token));
    }

}