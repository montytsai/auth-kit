package io.github.montytsai.authkit.service;

import io.github.montytsai.authkit.dto.RegisterRequest;
import io.github.montytsai.authkit.exception.UserAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class AuthService {

    /**
     * 用記憶體內 & 執行續安全的 Map 模擬資料庫
     */
    private final Map<String, String> userStore = new ConcurrentHashMap<>();

    private final PasswordEncoder passwordEncoder;

    public AuthService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 註冊
     */
    public void register(RegisterRequest registerRequest) {
        log.info("Attempting to register new user with email: {}", registerRequest.getEmail());

        // 檢查使用者是否已存在
        if (userStore.containsKey(registerRequest.getEmail())) {
            log.warn("Registration failed: Email {} already exists.", registerRequest.getEmail());
            throw new UserAlreadyExistsException("User with email " + registerRequest.getEmail() + " already exists.");
        }

        // 加密密碼
        String hashedPassword = passwordEncoder.encode(registerRequest.getPassword());
        // 在控制台印出 Log，方便驗證密碼真的被加密
        log.debug("Password successfully hashed for email: {}", registerRequest.getEmail());

        userStore.put(registerRequest.getEmail(), hashedPassword);
        log.info("User {} registered successfully.", registerRequest.getEmail());
        log.debug("Current user store state: {}", userStore);
    }

}