package com.noodleesystem.gateway.controller;

import java.util.Date;

import com.noodleesystem.gateway.exception.UnauthorizedAccessException;
import com.noodleesystem.gateway.model.UserCredentialsModel;
import com.noodleesystem.gateway.repository.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.noodleesystem.gateway.model.UserApiModel;

@RestController
public class AuthenticationController {
    @Autowired
    private UserRepository usersRepository;

    @PostMapping("/auth")
    public String authenticate(@RequestBody UserCredentialsModel user) {
        final long currentTimeMillis = System.currentTimeMillis();
        final long tokenExpirationTimeMs = 3600 * 1000;
        final String signatureKey = "c9e0fffa-6333-47d9-b621-ba44dc9523a7";

        UserApiModel userObject = usersRepository.findByUsername(user.getUsername());

        if (userObject != null && user.getPassword().equals(userObject.getPassword())) {
            return Jwts.builder()
                    .setSubject(user.getUsername())
                    .claim("roles", "student")
                    .setIssuedAt(new Date(currentTimeMillis))
                    .setExpiration(new Date(currentTimeMillis + tokenExpirationTimeMs))
                    .signWith(SignatureAlgorithm.HS512, signatureKey)
                    .compact();
        } else {
            throw new UnauthorizedAccessException();
        }
    }



//    @GetMapping("/receive")
//    public String get() {
//        Object messageObject = rabbitTemplate.receiveAndConvert(queueName);
//
//        if (messageObject != null) {
//            String message = messageObject.toString();
//            Log.information("{message} message was read from {queue} queue!", message, queueName);
//            return message;
//        } else {
//            String errorMessage = MessageFormat.format("No message in queue {0}!", queueName);
//            throw new EmptyQueueException(errorMessage);
//        }
//    }
}
