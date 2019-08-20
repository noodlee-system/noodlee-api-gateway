package com.noodleesystem.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import serilogj.Log;

@EnableDiscoveryClient
@EnableZuulProxy
@SpringBootApplication
public class ApiGatewayService {
    public static void main(String[] args) {
		SpringApplication.run(ApiGatewayService.class, args);

        Log.information("{serviceName} is running...", "API Gateway Service");
	}
}
