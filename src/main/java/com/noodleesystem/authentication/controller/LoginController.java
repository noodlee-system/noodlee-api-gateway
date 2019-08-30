package com.noodleesystem.authentication.controller;

import java.util.Date;

import com.noodleesystem.authentication.exception.UnauthorizedAccessException;
import com.noodleesystem.authentication.model.Token;
import com.noodleesystem.authentication.model.UserLoginCommand;
import com.noodleesystem.authentication.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.noodleesystem.authentication.model.UserRegistrationModel;

@RestController
public class LoginController {
    @Autowired
    private UserRepository usersRepository;
    
    @PostMapping("/login")
    public Token login(@RequestBody UserLoginCommand user) {
        final long currentTimeMs = System.currentTimeMillis();
        final long tokenExpirationTimeMs = 3600 * 1000;
        final String signatureKey = "c9e0fffa-6333-47d9-b621-ba44dc9523a7";

        UserRegistrationModel userObject = usersRepository.findByUsername(user.getUsername());

        if (userObject != null && user.getPassword().equals(userObject.getPassword())) {
            final long expirationTime = currentTimeMs + tokenExpirationTimeMs;

            final String token = Jwts.builder()
                    .setSubject(user.getUsername())
                    .claim("roles", "student")
                    .setIssuedAt(new Date(expirationTime))
                    .setExpiration(new Date(expirationTime))
                    .signWith(SignatureAlgorithm.HS512, signatureKey)
                    .compact();

            Token tokenResponseObject = new Token();
            tokenResponseObject.setUserId(userObject.getId());
            tokenResponseObject.setUsername(userObject.getUsername());
            tokenResponseObject.setCreationTime(currentTimeMs);
            tokenResponseObject.setExpirationTime(expirationTime);
            tokenResponseObject.setToken(token);
            tokenResponseObject.setRole(userObject.getRole());

            return tokenResponseObject;
        } else {
            throw new UnauthorizedAccessException();
        }
    }
}
