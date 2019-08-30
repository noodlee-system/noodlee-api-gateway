package com.noodleesystem.authentication.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    static final String userRegistrationQueue = "user_registration_queue";

    @Bean
    public Queue getUserRegistrationQueue() {
        return new Queue(userRegistrationQueue);
    }
}

