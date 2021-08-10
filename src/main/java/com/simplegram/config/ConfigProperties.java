package com.simplegram.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "application")
public class ConfigProperties {
    private String uploadPath;
    private int jwtExpirationMs;
    private String jwtSecret;
    private String imageGenerator;
}
