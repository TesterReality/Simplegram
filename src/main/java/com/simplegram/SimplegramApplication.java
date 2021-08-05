package com.simplegram;

import com.simplegram.config.ConfigProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
public class SimplegramApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimplegramApplication.class, args);
	}

}
