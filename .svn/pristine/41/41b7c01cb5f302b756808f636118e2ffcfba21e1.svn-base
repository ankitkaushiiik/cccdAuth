package com.ccd.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication(scanBasePackages = "com.ccd")
@EnableAuthorizationServer
@EnableEncryptableProperties
public class CcdAuthenticationServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CcdAuthenticationServerApplication.class, args);
	}

}
