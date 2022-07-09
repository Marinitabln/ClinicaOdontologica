package com.example.clinicaOdontologica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class ClinicaOdontoApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClinicaOdontoApplication.class, args);
	}

	@Configuration
	@EnableWebMvc
	public class WebConfig implements WebMvcConfigurer {

		@Override
		public void addCorsMappings(CorsRegistry registry) {

			registry.addMapping("/**")
					.allowedOriginPatterns("*")
					.allowCredentials(true)
					.allowedMethods("GET", "POST", "PUT", "DELETE")
					.maxAge(3600);
		}
	}

}
