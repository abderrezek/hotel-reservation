package com.abderrezek.hotelreservations.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class EmailConfiguration {
	
	@Value("${spring.mail.host}")
	private String host;
	@Value("${spring.mail.port}")
	private Integer port;
	@Value("${spring.mail.ssl}")
	private boolean ssl;
	@Value("${spring.mail.tls}")
	private boolean tls;
	@Value("${spring.mail.username}")
	private String username;
	@Value("${spring.mail.password}")
	private String password;

}
