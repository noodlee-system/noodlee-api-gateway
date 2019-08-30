package com.noodleesystem.authentication.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.MessageFormat;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "")
public class UserExistsException extends RuntimeException {
    public UserExistsException(final String username) {
        super(MessageFormat.format("User {0} is already exists!", username));
    }
}