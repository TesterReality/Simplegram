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
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.UUID;

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
    private String userAvatarName = UUID.randomUUID().toString().concat(".png");

    @Async
    @SneakyThrows(IOException.class)
    public void loadAvatarFromUrl(String userLogin, String userId) {
        String url = config.getImageGenerator() + "/" + userLogin + ".png";
        String headerValue = "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)";

        File userAvatarsDir = Paths.get(config.getUploadPath(), "avatars", userAvatarName).toFile();
        FileUtils.createParentDirectories(userAvatarsDir);

        RequestCallback requestCallback = request -> request
                .getHeaders().add("User-Agent", headerValue);

        ResponseExtractor<Void> responseExtractor = response -> {
            try (FileOutputStream out = new FileOutputStream(userAvatarsDir)) {
                IOUtils.copy(response.getBody(), out);
            }
            return null;
        };

        restTemplate.execute(url, HttpMethod.GET, requestCallback, responseExtractor);

        userRepository.updateAvatarByUUID(userId, userAvatarName);
    }
}
