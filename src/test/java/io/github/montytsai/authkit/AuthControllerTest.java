package io.github.montytsai.authkit;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.montytsai.authkit.dto.LoginRequest;
import io.github.montytsai.authkit.dto.RegisterRequest;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    @DisplayName("成功註冊應回傳 201 Created")
    void whenRegisterNewUser_thenReturns201Created() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("newuser@example.com");
        request.setPassword("password123");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(content().string("User registered successfully."));
    }

    @Test
    @Order(2)
    @DisplayName("註冊已存在帳號應回傳 409 Conflict")
    void whenRegisterExistingUser_thenReturns409Conflict() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("newuser@example.com");
        request.setPassword("password123");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }

    @Test
    @Order(3)
    @DisplayName("密碼長度不足應回傳 400 Bad Request")
    void whenRegisterWithShortPassword_thenReturns400BadRequest() throws Exception {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("shortpass@example.com");
        request.setPassword("123");

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Order(4)
    @DisplayName("登入成功應回傳 200 OK 並含 JWT Token")
    void whenLoginWithCorrectCredentials_thenReturns200AndToken() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("newuser@example.com");
        loginRequest.setPassword("password123");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").value(containsString("dummy-jwt-token")));
    }

    @Test
    @Order(5)
    @DisplayName("登入失敗應回傳 401 Unauthorized")
    void whenLoginWithWrongPassword_thenReturns401() throws Exception {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("newuser@example.com");
        loginRequest.setPassword("wrongpassword");

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized());
    }

}