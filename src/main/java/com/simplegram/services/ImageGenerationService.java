package com.simplegram.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
@Log4j2
@Component
@ConfigurationProperties("img")
public class ImageGenerationService {

    private final MessageSource messageSource;
    private String imageGenerator;

    public String loadAvatarFromUrl(String userLogin, String uploadsDir) {
        URLConnection uc = null;
        URL url = null;
        InputStream urlStream = null;
        try {
            url = new URL(imageGenerator + "/" + userLogin + ".png");
            uc = url.openConnection();
            uc.setRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            uc.connect();
            urlStream = uc.getInputStream();

            /*
            try (InputStream is = uc.getInputStream()) {
                IOUtils.copy(is, new FileOutputStream("fdsf"));
            }*/

            BufferedImage image = ImageIO.read(urlStream);
            return saveToAvatarDir(image, uploadsDir);
        } catch (MalformedURLException e) {
            log.error(e, e);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(messageSource.getMessage("error.loadAvatars",
                    null, Locale.ENGLISH));
        }
        return null;
    }

    private String saveToAvatarDir(BufferedImage image, String uploadsDir) {
        String pathToUserAvatrs = uploadsDir + "avatars/";
        String fileName = UUID.randomUUID().toString() + ".png";
        File dirUpload = new File(pathToUserAvatrs);
        File f = new File(dirUpload.getAbsolutePath());
        if (!f.exists()) {
            f.mkdirs();
        }

        File outputfile = new File(dirUpload.getAbsolutePath() + "/" + fileName);
        try {
            ImageIO.write(image, "png", outputfile);
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            // TODO: 04.08.2021 logs without translation
            log.error(messageSource.getMessage("error.loadAvatarWithResource",
                    null, Locale.ENGLISH));
        }
        return null;
    }
}
