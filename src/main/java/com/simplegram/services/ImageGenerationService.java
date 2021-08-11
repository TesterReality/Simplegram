package com.simplegram.services;

import com.simplegram.config.ConfigProperties;
import com.simplegram.entity.User;
import com.simplegram.exceptions.BadRequestException;
import com.simplegram.exceptions.ServiceException;
import com.simplegram.repository.UserRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Paths;
import java.util.Locale;
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
    public void loadAvatarFromUrl(User user) {
        try {
            URL url = new URL(config.getImageGenerator() + "/" + user.getLogin() + ".png");
            URLConnection uc = url.openConnection();
            uc.setRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            uc.connect();
            String userAvatarName = UUID.randomUUID().toString().concat(".png");
            File userAvatarsDir = Paths.get(config.getUploadPath(), "avatars").toFile();

            try (InputStream is = uc.getInputStream();
                 FileOutputStream out = new FileOutputStream(String.valueOf(Paths.get(userAvatarsDir.getAbsolutePath(), userAvatarName)))) {
                IOUtils.copy(is, out);
            }
            user.setAvatar(userAvatarName);
            userRepository.save(user);
            return;
        } catch (MalformedURLException e) {
            log.error(e, e);
        } catch (IOException e) {
            log.error(messageSource.getMessage("error.loadAvatars",
                    null, Locale.getDefault()));
        }
        log.error(messageSource.getMessage("exception.imageGeneratedException",
                null, Locale.getDefault()));
        throw new BadRequestException("user.answer.errorCreateAccount");

    }
}
