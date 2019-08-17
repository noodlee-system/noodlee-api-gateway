package com.noodleesystem.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import serilogj.Log;

@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayService {
    public static void main(String[] args) {
		SpringApplication.run(ApiGatewayService.class, args);

        Log.information("{serviceName} is running...", "API Gateway Service");
	}
}
