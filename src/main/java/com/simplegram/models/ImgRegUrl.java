package com.simplegram.models;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Locale;

@Log4j2
public class ImgRegUrl {
    private String uploadsDir;
    private String urlImage;
    private String userLogin;

    @Autowired
    private MessageSource messageSource;

    public ImgRegUrl(String urlImage, String userLogin, String uploadsDir) {
        this.urlImage = urlImage;
        this.userLogin = userLogin;
        this.uploadsDir = uploadsDir;
    }

    public void loadAvatarFromUrl() {
        URLConnection uc = null;
        URL url = null;
        InputStream urlStream = null;
        try {
            url = new URL(urlImage);
            uc = url.openConnection();
            uc.setRequestProperty("User-Agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
            uc.connect();
            urlStream = uc.getInputStream();
            BufferedImage image = ImageIO.read(urlStream);
            saveToAvatarDir(image);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            log.error(messageSource.getMessage("error.urlAvatar",
                    null, Locale.ENGLISH));
        } catch (IOException e) {
            e.printStackTrace();
            log.error(messageSource.getMessage("error.loadAvatars",
                    null, Locale.ENGLISH));
        }
    }

    private void saveToAvatarDir(BufferedImage image) {
        String pathToUserAvatrs = uploadsDir + "avatars/";
        String fileName = DigestUtils.md5Hex(userLogin) + ".png";
        File dirUpload = new File(pathToUserAvatrs);
        File f = new File(dirUpload.getAbsolutePath());
        if (!f.exists()) {
            f.mkdirs();
        }

        File outputfile = new File(dirUpload.getAbsolutePath() + "/" + fileName);
        try {
            ImageIO.write(image, "png", outputfile);
        } catch (IOException e) {
            e.printStackTrace();
            log.error(messageSource.getMessage("error.loadAvatarWithResource",
                    null, Locale.ENGLISH));
        }
    }
}
