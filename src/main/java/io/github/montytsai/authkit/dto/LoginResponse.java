package io.github.montytsai.authkit.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 用來定義登入成功後回傳的 JSON 格式。
 */
@Data
@AllArgsConstructor
public class LoginResponse {

    private String message;

    // TODO 先放一個假的 Token
    private String token;

}