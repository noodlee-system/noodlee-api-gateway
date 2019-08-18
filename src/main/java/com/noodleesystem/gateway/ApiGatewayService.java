package com.noodleesystem.gateway;

import com.noodlee.system.gateway.authentication.JwtFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import serilogj.Log;

import java.util.Collections;

@EnableDiscoveryClient
@EnableZuulProxy
@SpringBootApplication
public class ApiGatewayService {
    public static void main(String[] args) {
		SpringApplication.run(ApiGatewayService.class, args);

        Log.information("{serviceName} is running...", "API Gateway Service");
	}

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new JwtFilter());
        filterRegistrationBean.setUrlPatterns(Collections.singleton("/api/*"));
        return filterRegistrationBean;
    }
}
