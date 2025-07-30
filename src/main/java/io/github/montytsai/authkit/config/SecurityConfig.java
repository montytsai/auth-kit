package io.github.montytsai.authkit.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * **Spring Security 配置類。**
 * 定義應用程式的 Web 安全策略：HTTP 請求授權、CSRF 禁用與認證相關 Bean。
 *
 * @apiNote 針對 RESTful API 需求客製化了 Spring Security 的預設行為。
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * **配置 Spring Security 的 HTTP 安全過濾鏈**
     * 定義請求授權規則及核心安全設定
     *
     * @param http {@link HttpSecurity} 用於配置 web 安全。
     * @return 配置好的 {@link SecurityFilterChain} 實例。
     * @throws Exception 如果配置過程中發生錯誤。
     *
     * @implSpec
     * - **CSRF 禁用：** 適用於無狀態 RESTful API (如 JWT 認證)，降低了 CSRF 攻擊風險並簡化前後端。
     * - **授權規則：** `/api/auth/**` 允許匿名訪問；其餘任何請求均需身份驗證。
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/api/auth/**").permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }

    /**
     * **定義並公開 {@link AuthenticationManager} Bean**
     * 負責協調和執行身份驗證請求，利用已配置的 {@link org.springframework.security.core.userdetails.UserDetailsService}
     * 和 {@link PasswordEncoder}。
     *
     * @param authConfig {@link AuthenticationConfiguration} 提供獲取能力的上下文。
     * @return {@link AuthenticationManager} 實例。
     * @throws Exception 如果獲取過程中發生錯誤。
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    /**
     * **密碼加密器 Bean (PasswordEncoder)。**
     * 使用 {@link BCryptPasswordEncoder} 進行密碼單向雜湊，確保安全儲存。
     * BCrypt 提供加鹽及可配置的計算成本，有效抵抗暴力破解。
     *
     * @return {@link PasswordEncoder} 實例。
     * @apiNote 完整的密碼策略應結合複雜度要求、帳戶鎖定等。
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}