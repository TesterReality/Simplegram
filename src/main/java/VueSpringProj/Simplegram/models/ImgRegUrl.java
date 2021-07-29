package VueSpringProj.Simplegram.models;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class ImgRegUrl {

    private String uploadsDir;
    private String urlImage;
    private String userLogin;

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
            System.out.println("Не удалось создать автарку для пользователя");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Не удалось загрузить автарку для пользователя");
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
            System.out.println("Не удалось загрузить аватарку с ресурса");
        }
    }
}
