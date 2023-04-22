package com.plagarism.detect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
// import org.springframework.context.annotation.Bean;
// import org.springframework.web.servlet.config.annotation.CorsRegistry;
// import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class DetectApplication {

	public static void main(String[] args) {
		SpringApplication.run(DetectApplication.class, args);
	}
	
	//global cors if we need to do so
	// @Bean
	// public WebMvcConfigurer corsConfigurer() {
	// 	return new WebMvcConfigurer() {
	// 		@Override
	// 		public void addCorsMappings(CorsRegistry registry) {
	// 			// registry.addMapping("/greeting-javaconfig").allowedOrigins("http://localhost:8080");
	// 		}
	// 	};
	// }
}
