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
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
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

    @Async
    @SneakyThrows(IOException.class)
    public void loadAvatarFromUrl(String userLogin, String userId) {
        URL url = new URL(config.getImageGenerator() + "/" + userLogin + ".png");
        URLConnection uc = url.openConnection();
        uc.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
        uc.connect();

        String userAvatarName = UUID.randomUUID().toString().concat(".png");
        File userAvatarsDir = Paths.get(config.getUploadPath(), "avatars", userAvatarName).toFile();
        FileUtils.createParentDirectories(userAvatarsDir);

        try (InputStream is = uc.getInputStream()) {
            try (FileOutputStream out = new FileOutputStream(userAvatarsDir)) {
                IOUtils.copy(is, out);
            }
        }
        userRepository.updateAvatarByUUID(userId, userAvatarName);
    }
}
