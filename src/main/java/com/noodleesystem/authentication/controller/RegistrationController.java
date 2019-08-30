package com.noodleesystem.authentication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.noodleesystem.authentication.exception.UserExistsException;
import com.noodleesystem.authentication.model.UserRegistrationModel;
import com.noodleesystem.authentication.model.UserRegistrationCommand;
import com.noodleesystem.authentication.repository.UserRepository;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import serilogj.Log;

@RestController
public class RegistrationController {
    final static String userRegistrationQueue = "user_registration_queue";

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Autowired
    private UserRepository usersRepository;

    private ObjectMapper objectMapper;

    public RegistrationController() {
        this.objectMapper = new ObjectMapper();
    }

    @PostMapping("/register")
    public UserRegistrationCommand registerUser(@RequestBody UserRegistrationCommand user) throws Exception {
        UserRegistrationModel userObject = usersRepository.findByUsername(user.getUsername());

        if (userObject != null) {
            throw new UserExistsException(user.getUsername());
        }

        try {
            final String userJsonString = objectMapper.writeValueAsString(user);

            rabbitTemplate.convertAndSend(userRegistrationQueue, userJsonString);
        } catch (JsonProcessingException ex) {
            throw new Exception("Problem with processing your request!");
        } catch (AmqpException ex) {
            System.err.println("WARNING: Connection with RabbitMQ failed!");
        }

        final UserRegistrationModel newUserCredentials = new UserRegistrationModel(user.getUsername(), user.getPassword());
        newUserCredentials.setRole("student");

        usersRepository.save(newUserCredentials);

        Log.information("{username} user was successfully registered!", user.getUsername());

        return user;
    }
}
