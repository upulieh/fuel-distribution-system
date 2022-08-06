package com.cpc.orderservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// the frontend application port
		registry.addMapping("/**").allowedOriginPatterns("http://localhost:3000").allowCredentials(true); //http://localhost:3000
	}
}