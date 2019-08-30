package com.noodleesystem.authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import serilogj.Log;

@SpringBootApplication
public class AuthenticationService {
    public static void main(String[] args) {
		SpringApplication.run(AuthenticationService.class, args);

        Log.information("{serviceName} is running...", "Authentication Service");
	}
}
