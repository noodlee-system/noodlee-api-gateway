package com.noodleesystem.gateway.controller;

import java.util.Date;

import com.noodleesystem.gateway.exception.UnauthorizedAccessException;
import com.noodleesystem.gateway.model.Token;
import com.noodleesystem.gateway.model.UserAuthenticationCommand;
import com.noodleesystem.gateway.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.noodleesystem.gateway.model.UserRegistrationModel;

@RestController
public class AuthenticationController {
    @Autowired
    private UserRepository usersRepository;

    @CrossOrigin(origins = "http://localhost:4200")
    @PostMapping("/auth")
    public Token authenticate(@RequestBody UserAuthenticationCommand user) {
        final long currentTimeMillis = System.currentTimeMillis();
        final long tokenExpirationTimeMs = 3600 * 1000;
        final String signatureKey = "c9e0fffa-6333-47d9-b621-ba44dc9523a7";

        UserRegistrationModel userObject = usersRepository.findByUsername(user.getUsername());

        if (userObject != null && user.getPassword().equals(userObject.getPassword())) {
            final String token = Jwts.builder()
                    .setSubject(user.getUsername())
                    .claim("roles", "student")
                    .setIssuedAt(new Date(currentTimeMillis))
                    .setExpiration(new Date(currentTimeMillis + tokenExpirationTimeMs))
                    .signWith(SignatureAlgorithm.HS512, signatureKey)
                    .compact();

            Token tokenResponseObject = new Token();
            tokenResponseObject.setCreationTime(currentTimeMillis);
            tokenResponseObject.setExpirationTime(tokenExpirationTimeMs);
            tokenResponseObject.setToken(token);
            tokenResponseObject.setUserRole("student");

            return tokenResponseObject;
        } else {
            throw new UnauthorizedAccessException();
        }
    }
}
