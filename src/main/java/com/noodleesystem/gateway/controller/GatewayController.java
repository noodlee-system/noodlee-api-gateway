package com.noodleesystem.gateway.controller;

import java.text.MessageFormat;
import java.util.List;

import com.noodleesystem.gateway.exception.EmptyQueueException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.noodleesystem.gateway.model.User;
import com.noodleesystem.gateway.repository.UserRepository;
import org.springframework.web.client.HttpClientErrorException;
import serilogj.Log;

@RestController
@RequestMapping("/api")
public class GatewayController {
    final static String queueName = "gateway_queue";

    @Autowired
	private UserRepository usersRepository;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @GetMapping("/users")
    List<User> getAllUsers() {
        return usersRepository.findAll();
	}

    @GetMapping("/send")
    String sendToQueue(@RequestParam(value = "message", defaultValue = "Default message") String message) {
        rabbitTemplate.convertAndSend(queueName, message);
        Log.information("{message} message was sent to {queue} queue!",message, queueName);
        return String.format("Message %s sent!", message);
    }

    @GetMapping("/receive")
    public String get() {
        Object messageObject = rabbitTemplate.receiveAndConvert(queueName);

        if (messageObject != null) {
            String message = messageObject.toString();
            Log.information("{message} message was read from {queue} queue!", message, queueName);
            return message;
        } else {
            String errorMessage = MessageFormat.format("No message in queue {0}!", queueName);
            throw new EmptyQueueException(errorMessage);
        }
    }
}
