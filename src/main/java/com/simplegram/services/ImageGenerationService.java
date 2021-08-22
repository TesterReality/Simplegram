package com.simplegram.services;

import com.simplegram.config.ConfigProperties;
import com.simplegram.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.context.MessageSource;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.http.converter.BufferedImageHttpMessageConverter;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.*;

@Getter
@Setter
@RequiredArgsConstructor
@Log4j2
@Component
public class ImageGenerationService {
    private final MessageSource messageSource;
    private final ConfigProperties config;
    private final UserRepository userRepository;
    private final RestTemplate restTemplate;

    @Async("threadPoolTaskExecutor")
    @SneakyThrows({IOException.class})
    public void loadAvatarFromUrl(String userLogin, String userId) {
        String userAvatarName = UUID.randomUUID().toString().concat(".png");
        String url = config.getImageGenerator() + "/" + userLogin + ".png";
        String headerValue = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)";

        File userAvatarsDir = Paths.get(config.getUploadPath(), "avatars", userAvatarName).toFile();
        FileUtils.createParentDirectories(userAvatarsDir);

        HttpHeaders headers = new HttpHeaders();
        headers.set("User-Agent", headerValue);
        HttpEntity entity = new HttpEntity(headers);

        restTemplate.getMessageConverters().add(
                new ByteArrayHttpMessageConverter());

        ResponseEntity<byte[]> responseEntity= restTemplate.exchange(url, HttpMethod.GET, entity, byte[].class);

        try(InputStream in = new ByteArrayResource(responseEntity.getBody()).getInputStream()) {
            try (FileOutputStream out = new FileOutputStream(userAvatarsDir)) {
                IOUtils.copy(in, out);
            }
        }
        userRepository.updateAvatarByUUID(userId, userAvatarName);
    }
}
