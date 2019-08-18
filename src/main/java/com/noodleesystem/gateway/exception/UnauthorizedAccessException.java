package com.noodleesystem.gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED, reason = "")
public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException() {
        super("Invalid username or password!");
    }
}