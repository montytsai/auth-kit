package io.github.montytsai.authkit.service;

import io.github.montytsai.authkit.dto.RegisterRequest;
import io.github.montytsai.authkit.exception.UserAlreadyExistsException;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 認證服務 (Authentication Service)。
 * 此服務承擔兩個核心職責：使用者註冊，以及作為 Spring Security 的 UserDetailsService 載入使用者詳細資訊。
 *
 * @implNote 目前使用 {@link ConcurrentHashMap} 作為臨時的記憶體儲存來模擬資料庫，
 *           以便快速啟動和測試。在生產環境中，這應替換為持久化的資料儲存層
 */
@Service
@Slf4j
public class AuthService implements UserDetailsService {

    /**
     * 臨時的記憶體使用者儲存。
     * @apiNote 此 Map 僅用於開發和演示目的。它會在應用程式重啟後丟失所有資料。
     * 對於實際應用，請導入資料庫持久化機制。
     */
    private final Map<String, String> userStore = new ConcurrentHashMap<>();

    private final PasswordEncoder passwordEncoder;

    public AuthService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * 註冊新使用者
     * - 此方法負責接收使用者的註冊請求，進行業務邏輯驗證（Email 唯一性），
     * - 加密密碼，並將新使用者資訊儲存到記憶體模擬的資料庫中。
     *
     * @param registerRequest 包含新使用者電子郵件和密碼的註冊請求資料傳輸物件 (DTO)。
     *                        輸入參數的格式驗證（如非空、長度）在 Controller 層使用 {@code @Valid} 處理。
     * @throws UserAlreadyExistsException 如果嘗試註冊的電子郵件已經存在，則拋出此業務異常。
     * @apiNote Email 被選為唯一的用戶識別符。任何嘗試使用現有 Email 註冊的請求將會被拒絕。
     */
    public void register(RegisterRequest registerRequest) {
        log.info("Attempting to register new user with email: {}", registerRequest.getEmail());

        // 檢查使用者 email 是否已存在。
        if (userStore.containsKey(registerRequest.getEmail())) {
            log.warn("Registration failed: Email {} already exists.", registerRequest.getEmail());
            throw new UserAlreadyExistsException("User with email " + registerRequest.getEmail() + " already exists.");
        }

        // 對用戶提供的明文密碼進行雜湊處理。
        String hashedPassword = passwordEncoder.encode(registerRequest.getPassword());
        log.debug("Password successfully hashed for email: {}", registerRequest.getEmail());

        // 資料持久化：將新用戶的 email 和加密後的密碼存入用戶儲存區。
        // FIXME: 在生產環境中，這裡需要替換為對資料庫操作的呼叫，以實現資料持久化。
        userStore.put(registerRequest.getEmail(), hashedPassword);
        log.trace("Current user store state: {}", userStore);

        log.info("User {} registered successfully.", registerRequest.getEmail());
    }

    /**
     * 根據 email 載入使用者詳細資訊。
     * 實作 Spring Security 用於使用者身份驗證的核心方法，在身份驗證流程中被框架調用。
     *
     * @param email 使用者的電子郵件地址
     * @return 包含使用者名稱、密碼和權限的 {@link UserDetails} 物件，供 Spring Security 進行密碼比對。
     * @throws UsernameNotFoundException 如果找不到指定電子郵件的使用者
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("Attempting to load user by email: {}", email);

        // 檢查使用者是否存在
        if (!userStore.containsKey(email)) {
            log.warn("User details not found for email: {}", email);
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        // 取得已加密的使用者密碼
        String hashedPassword = userStore.get(email);
        log.debug("Successfully retrieved user details for email: {}", email);

        // 返回 Spring Security 需要的 UserDetails 物件。
        log.debug("User details loaded for email: {}", email);
        // TODO: 加入使用者權限
        return new User(email, hashedPassword, Collections.emptyList());
    }

}