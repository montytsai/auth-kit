package io.github.montytsai.authkit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * **自定義業務異常：使用者已存在。**
 * 當嘗試註冊的使用者電子郵件已經存在於系統中時，會拋出此異常。
 */
@ResponseStatus(HttpStatus.CONFLICT) // HTTP 狀態碼 409 Conflict
public class UserAlreadyExistsException extends RuntimeException {

    public UserAlreadyExistsException(String message) {
        super(message);
    }

}