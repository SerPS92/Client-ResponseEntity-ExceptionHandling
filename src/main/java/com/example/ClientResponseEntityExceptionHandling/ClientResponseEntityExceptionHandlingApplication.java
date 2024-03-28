package com.example.ClientResponseEntityExceptionHandling;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.SpringVersion;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ClientResponseEntityExceptionHandlingApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClientResponseEntityExceptionHandlingApplication.class, args);
		System.out.println(SpringVersion.getVersion());
	}

	@Bean
	public RestTemplate restTemplate(){
		BasicAuthenticationInterceptor interceptor;
		interceptor = new BasicAuthenticationInterceptor("admin", "admin");
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(interceptor);
		return restTemplate;
	}

}
